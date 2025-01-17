<!-- 某个记录的跟进记录列表，目前主要用于 CRM 客户、商机等详情界面 -->
<template>
  <p style="float: left;margin-top: 10px" v-if="total_">
    <span>共{{total_}}份软件著作信息</span><span v-if="back_total!==total_">,系统仅获取了前{{back_total}}份</span>&nbsp;&nbsp;&nbsp;
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
        label="登记日期"
        prop="regtime"
        width="180px"
      />
      <el-table-column
        :formatter="dateFormatter"
        align="center"
        label="首次发表日期"
        prop="publishtime"
        width="180px"
      />
      <el-table-column align="center" label="著作权人"   prop="authorNationality"/>
      <el-table-column align="center" label="简称"  prop="simplename"/>
      <el-table-column align="center" label="登记号"   prop="regnum"/>
      <el-table-column align="center" label="分类号"  width="150px" prop="catnum"/>
      <el-table-column
        :formatter="dateFormatter"
        align="center"
        label="批准日期"
        prop="eventTime"
        width="180px"
      />
      <el-table-column align="center" label="全称"  prop="fullname"/>
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
        <el-descriptions-item label="登记日期">{{ formData.regtime }}</el-descriptions-item>
        <el-descriptions-item label="首次发表日期">{{ formatTimestamp2(formData.publishtime) }}</el-descriptions-item>
        <el-descriptions-item label="著作权人">{{ formData.authorNationality }}</el-descriptions-item>
        <el-descriptions-item label="作品简称">{{ formData.simplename }}</el-descriptions-item>
        <el-descriptions-item label="天眼查链接">{{ formData.connList }}</el-descriptions-item>
        <el-descriptions-item label="登记号">{{ formData.regnum }}</el-descriptions-item>
        <el-descriptions-item label="分类号">{{ formData.catnum }}</el-descriptions-item>
        <el-descriptions-item label="唯一标识符">{{ formData.uni }}</el-descriptions-item>
        <el-descriptions-item label="批准日期">{{ formatTimestamp2(formData.eventTime) }}</el-descriptions-item>
        <el-descriptions-item label="作品全称">{{ formData.fullname }}</el-descriptions-item>
        <el-descriptions-item label="版本号">{{ formData.version }}</el-descriptions-item>
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
import { CustomerPublicCopyrightInfoApi, CustomerPublicCopyrightInfoVO } from '@/api/crm/customer/customerpubliccopyrightinfo'
import { formatTimestamp2,calculateDaysDifference,dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import {DICT_TYPE, getIntDictOptions} from "@/utils/dict";
/** 跟进记录列表 */
defineOptions({ name: 'CopyrightList' })
const props = defineProps<{
  customerId: {type:Number,required:true},
  tid: {type:String,required:true},
  customerName:{type:String,required:true},
  keyword:string
}>()
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<CustomerPublicCopyrightInfoVO[]>([]) // 列表的数据
const total = ref(0) // 入库数量
const total_ = ref(0) // 总数量
const back_total = ref(0) // 总数量
const queryParams = reactive({
  pageNo: 1,
  pageSize: 5,
  customerId: 0,
  keyword: ''
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
    const data = await CustomerPublicCopyrightInfoApi.getCustomerPublicCopyrightInfoPage(queryParams)
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
    const response = await CustomerPublicCopyrightInfoApi.onlineUpdateCustomerPublicCopyrightInfo(updateParams);
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
const formData = ref<CustomerPublicCopyrightInfoVO>()
const handleDetail = (row:CustomerPublicCopyrightInfoVO) => {
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
    const data = await CustomerPublicCopyrightInfoApi.exportCustomerPublicCopyrightInfo(queryParams)
    download.excel(data, 'CRM-客户管理-公开信息-软件著作.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}
const limitDays = ref(0)
/**  */
onMounted(async () => {
  limitDays.value = getIntDictOptions(DICT_TYPE.CRM_CUSTOMER_ONLINEUPDATE_LIMIT)[0].value
  queryParams.customerId = props.customerId
  await getList()
})
// 监听 tid 属性的变化
const handleTidChange = (newVal) => {
  href_.value = `https://www.tianyancha.com/company/${newVal}/zhishi`;
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
