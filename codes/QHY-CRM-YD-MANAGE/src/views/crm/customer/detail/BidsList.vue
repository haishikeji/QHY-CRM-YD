<!-- 某个记录的跟进记录列表，目前主要用于 CRM 客户、商机等详情界面 -->
<template>
  <p style="float: left;margin-top: 10px" v-if="total_">
    <span>共{{total_}}份招投标信息</span><span v-if="back_total!==total_">,系统仅获取了前{{back_total}}份</span>&nbsp;&nbsp;&nbsp;
    <el-link  :href="href_" target="_blank" type="primary">点击查看所有数据</el-link>
  </p>
  <!-- 操作栏 -->
  <el-row class="mb-10px" justify="end" style="margin-right: 35px">
    <span v-if="showTime && updateTime" style="margin-right: 10px;margin-top: 5px;color: #808080;font-size: 13px">最近更新于 {{formatTimestamp2(updateTime)}}</span>
    <el-tooltip v-if="updateDisable"  class="box-item" effect="dark" content="数据已于近期更新，请勿频繁操作" placement="top-end" hide-after="10">
      <el-button @click="onlineUpdate" :disabled="updateDisable" :loading="updateLoading"    @mouseover="onMouseOver"  @mouseout="onMouseOut">
        <Icon v-if="!updateLoading" class="mr-5px" icon="ep:refresh" />{{ updateLoading ? '更新中,请稍后' : '联网更新' }}
      </el-button>
    </el-tooltip>
    <el-button  v-else @click="onlineUpdate" :disabled="updateDisable" :loading="updateLoading"   @mouseover="onMouseOver"  @mouseout="onMouseOut">
      <Icon v-if="!updateLoading" class="mr-5px" icon="ep:refresh" />  {{ updateLoading ? '更新中,请稍后' : '联网更新' }}
    </el-button>
    <el-button
      v-hasPermi="['crm:customer:export']"
      :loading="exportLoading"
      plain
      @click="handleExport"
      :disabled="!total_"
    >
      <Icon class="mr-5px" icon="ep:download" />
      导出
    </el-button>
  </el-row>
  <!-- 列表 -->
  <ContentWrap>
    <el-table  v-loading="loading" :data="list"
               :show-overflow-tooltip="true"
               :stripe="true"
               :header-cell-style="{
            textAlign: 'center',
            height: '40px',
            background: '#f5f7fa',
            color: '#606266',
        }"
    >
      <el-table-column
        :formatter="dateFormatter"
        align="center"
        label="发布日期"
        prop="publishTime"
        width="180px"
      />
      <el-table-column align="center" label="详情链接"  width="150px">
        <template v-slot="scope">
          <el-link :href="scope.row.bidUrl" target="_blank" type="primary">
            详情链接
          </el-link>
        </template>
      </el-table-column>
      <el-table-column align="center" label="标题">
        <template v-slot="scope">
          <el-link :href="scope.row.link" target="_blank" type="primary">
            {{ scope.row.title }}
          </el-link>
        </template>
      </el-table-column>
      <el-table-column prop="province" label="省份地区" align="center" />
      <el-table-column prop="type" label="信息类型" align="center"/>
      <el-table-column prop="bidAmount" label="中标金额" align="center"/>
      <el-table-column align="center" label="采购人" prop="purchaser" >
        <template v-slot="scope">
           {{scope.row.purchaser.indexOf("[")!=-1?'/':scope.row.purchaser}}
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="120px">
        <template #default="scope">
          <el-button link type="primary" @click="handleDetail(scope.row)"> 详情 </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      v-model:limit="queryParams.pageSize"
      v-model:page="queryParams.pageNo"
      :total="total"
      @pagination="getList"
    />
    <el-dialog v-model="dialogVisible" title="招投标详情" width="75%" draggable >
      <el-descriptions :column="2" border>
        <el-descriptions-item label="标题">{{formData.title }}</el-descriptions-item>
        <el-descriptions-item label="采购人">{{formData.purchaser.indexOf('[')!=-1?'/':formData.purchaser }}</el-descriptions-item>
        <el-descriptions-item label="发布时间">{{formatTimestamp2(formData.publishTime) }}</el-descriptions-item>
        <el-descriptions-item label="详情链接">{{formData.link }}</el-descriptions-item>
        <el-descriptions-item label="天眼查链接">{{formData.bidUrl }}</el-descriptions-item>
        <el-descriptions-item label="正文简介（无效）">{{formData.intro }}</el-descriptions-item>
        <el-descriptions-item label="正文信息" span="2">
          <span v-if="formData.content?.length>300">
            <div v-if="!isExpanded">
              {{formData.content? formData.content.slice(0, 200) + (formData.content.length > 120 ? '...' : ''):'' }}
              <el-button link type="primary"  @click="isExpanded = true">展开</el-button>
            </div>

            <div v-else>
              {{ formData.content }}
              <el-button link  type="primary" @click="isExpanded = false">收起</el-button>
            </div>
          </span>
          <span v-else>
            {{ formData.content }}
          </span>
          </el-descriptions-item>
        <el-descriptions-item label="摘要信息（无效）">{{formData.abs }}</el-descriptions-item>
        <el-descriptions-item label="代理机构">{{formData.proxy }}</el-descriptions-item>
        <el-descriptions-item label="信息类型">{{formData.type }}</el-descriptions-item>
        <el-descriptions-item label="省份地区">{{formData.province }}</el-descriptions-item>
        <el-descriptions-item label="供应商">{{formData.bidWinner }}</el-descriptions-item>
        <el-descriptions-item label="中标金额">{{formData.bidAmount }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="close">关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </ContentWrap>
</template>

<script lang="ts" setup>
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { CustomerPublicBidsInfoApi, CustomerPublicBidsInfoVO } from '@/api/crm/customer/customerpublicbidsinfo'
import { formatTimestamp2,calculateDaysDifference,dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
/** 跟进记录列表 */
defineOptions({ name: 'BidsList' })
const props = defineProps<{
  customerId: {type:Number,required:true},
  tid: {type:String,required:true},
  customerName:{type:String,required:true},
  keyword:string
}>()
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<CustomerPublicBidsInfoVO[]>([]) // 列表的数据
const total = ref(0) // 入库数量
const total_ = ref(0) // 总数量
const back_total = ref(0) // 总数量
const queryParams = reactive({
  pageNo: 1,
  pageSize: 5,
  customerId: 0,
  keyword:undefined
})
const updateParams = reactive({
  customerId:null,
  customerName:''
})
const isExpanded = ref(false);

/** 查询列表 */
const getList = async () => {
  loading.value = true
  if (queryParams.pageSize==1){
    updateLoading.value = true
  }

  try {
    const data = await CustomerPublicBidsInfoApi.getCustomerPublicBidsInfoPage(queryParams)
    list.value = data.page.list
    total.value = data.page.total
    total_.value = data.totals
    back_total.value = data.back_total
    updateTime.value = data.updateTime
    updateDisable.value = calculateDaysDifference(updateTime.value, new Date) < limitDays.value
  } finally {
    updateLoading.value = false
    loading.value = false
  }
}
const updateTime = ref(new Date());
const updateDisable = ref(false)
const showTime = ref(false)
const updateLoading = ref(false)
const onMouseOver = () =>{
  showTime.value = true
}
const onMouseOut = () =>{
  showTime.value = false
}
/** 联网更新按钮操作 */
const onlineUpdate = async () => {
  // 更新请求参数
  updateParams.customerId = props.customerId;
  updateParams.customerName = props.customerName;
  // 显示加载状态
  updateLoading.value = true;
  try {
    // 发起联网更新请求
    const response = await CustomerPublicBidsInfoApi.onlineUpdateCustomerPublicBidsInfo(updateParams);
    // 检查响应是否成功
    if (response) {
      message.success(t('common.onlineUpdateSuccess'));
      // 刷新列表数据
      await getList();
    }
  } catch (error) {
  } finally {
    // 结束加载状态
    updateLoading.value = false;
  }
};

const dialogVisible = ref(false)
const formData = ref<CustomerPublicBidsInfoVO>()
const handleDetail = (row:CustomerPublicBidsInfoVO) => {
  dialogVisible.value = true
  formData.value = row
}
const close = () => {
  dialogVisible.value = false
  isExpanded.value = false
}
/** 删除按钮操作 */
// const handleDelete = async (id: number) => {
  // try {
  //   // 删除的二次确认
  //   await message.delConfirm()
  //   // 发起删除
  //   await FollowUpRecordApi.deleteFollowUpRecord(id)
  //   message.success(t('common.delSuccess'))
  //   // 刷新列表
  //   await getList()
  // } catch {}
// }

const href_ = ref('')
const exportLoading = ref(false) // 导出的加载中
/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await CustomerPublicBidsInfoApi.exportCustomerPublicBidsInfo(queryParams)
    download.excel(data, 'CRM-客户管理-公开信息-招投标.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
const limitDays = ref(0)
/**  */
onMounted(async () => {
  queryParams.customerId = props.customerId
  limitDays.value = getIntDictOptions(DICT_TYPE.CRM_CUSTOMER_ONLINEUPDATE_LIMIT)[0].value
  await getList()
})
// 监听 tid 属性的变化
const handleTidChange = (newVal) => {
  href_.value = `https://www.tianyancha.com/company/${newVal}/jingzhuang`;
};
watch(() => props.tid, handleTidChange);

const handleGetCustomerByKeyword = (newVal)=>{
  queryParams.keyword = newVal
  getList()
}
// const matchKeyword = (val) =>{
// }
watch(() => props.keyword, handleGetCustomerByKeyword);
</script>


<style lang="scss" scoped>
:deep(.el-descriptions__body) {
  .el-descriptions__table.is-bordered .el-descriptions__cell {
    min-width: 150px;
    word-break: break-all; // 让内容超出列宽时自动换行显示
    word-wrap: break-word;
  }
}
</style>
