/**
 * @author nicolas.peters
 * 
 * Contains all strings for the default language (en-us).
 * Version 1 - 08/29/08
 */
if(!ORYX) var ORYX = {};

if(!ORYX.I18N) ORYX.I18N = {};

ORYX.I18N.Language = "en_us"; //Pattern <ISO language code>_<ISO country code> in lower case!

if(!ORYX.I18N.Oryx) ORYX.I18N.Oryx = {};

ORYX.I18N.Oryx.title		= "Oryx";
ORYX.I18N.Oryx.noBackendDefined	= "注意! \n没有后端定义.\n 请求的模型没有被加载. 尝试加载保存配置插件.";
ORYX.I18N.Oryx.pleaseWait 	= "正在加载，请等待...";
ORYX.I18N.Oryx.notLoggedOn = "不能登录";
ORYX.I18N.Oryx.editorOpenTimeout = "编辑器还没有被启动. 请检查是否启用了弹出窗口拦截器，请禁用弹出窗口拦截器或允许当前站点弹出窗口.";

if(!ORYX.I18N.AddDocker) ORYX.I18N.AddDocker = {};

ORYX.I18N.AddDocker.group = "Docker";
ORYX.I18N.AddDocker.add = "Add Docker";
ORYX.I18N.AddDocker.addDesc = "新建折点";
ORYX.I18N.AddDocker.del = "Delete Docker";
ORYX.I18N.AddDocker.delDesc = "删除折点";

if(!ORYX.I18N.Arrangement) ORYX.I18N.Arrangement = {};

ORYX.I18N.Arrangement.groupZ = "Z-Order";
ORYX.I18N.Arrangement.btf = "Bring To Front";
ORYX.I18N.Arrangement.btfDesc = "Bring to Front";
ORYX.I18N.Arrangement.btb = "Bring To Back";
ORYX.I18N.Arrangement.btbDesc = "Bring To Back";
ORYX.I18N.Arrangement.bf = "Bring Forward";
ORYX.I18N.Arrangement.bfDesc = "Bring Forward";
ORYX.I18N.Arrangement.bb = "Bring Backward";
ORYX.I18N.Arrangement.bbDesc = "Bring Backward";
ORYX.I18N.Arrangement.groupA = "Alignment";
ORYX.I18N.Arrangement.ab = "Alignment Bottom";
ORYX.I18N.Arrangement.abDesc = "横向底部对齐";
ORYX.I18N.Arrangement.am = "Alignment Middle";
ORYX.I18N.Arrangement.amDesc = "横向中间对齐";
ORYX.I18N.Arrangement.at = "Alignment Top";
ORYX.I18N.Arrangement.atDesc = "横向顶部对齐";
ORYX.I18N.Arrangement.al = "Alignment Left";
ORYX.I18N.Arrangement.alDesc = "竖向左对齐";
ORYX.I18N.Arrangement.ac = "Alignment Center";
ORYX.I18N.Arrangement.acDesc = "竖向中间对齐";
ORYX.I18N.Arrangement.ar = "Alignment Right";
ORYX.I18N.Arrangement.arDesc = "竖向右对齐";
ORYX.I18N.Arrangement.as = "Alignment Same Size";
ORYX.I18N.Arrangement.asDesc = "相同大小";

if(!ORYX.I18N.Edit) ORYX.I18N.Edit = {};

ORYX.I18N.Edit.group = "Edit";
ORYX.I18N.Edit.cut = "Cut";
ORYX.I18N.Edit.cutDesc = "剪切";
ORYX.I18N.Edit.copy = "Copy";
ORYX.I18N.Edit.copyDesc = "复制";
ORYX.I18N.Edit.paste = "Paste";
ORYX.I18N.Edit.pasteDesc = "粘贴";
ORYX.I18N.Edit.del = "Delete";
ORYX.I18N.Edit.delDesc = "删除";

if(!ORYX.I18N.EPCSupport) ORYX.I18N.EPCSupport = {};

ORYX.I18N.EPCSupport.group = "EPC";
ORYX.I18N.EPCSupport.exp = "Export EPC";
ORYX.I18N.EPCSupport.expDesc = "Export diagram to EPML";
ORYX.I18N.EPCSupport.imp = "Import EPC";
ORYX.I18N.EPCSupport.impDesc = "Import an EPML file";
ORYX.I18N.EPCSupport.progressExp = "Exporting model";
ORYX.I18N.EPCSupport.selectFile = "Select an EPML (.empl) file to import.";
ORYX.I18N.EPCSupport.file = "File";
ORYX.I18N.EPCSupport.impPanel = "Import EPML File";
ORYX.I18N.EPCSupport.impBtn = "Import";
ORYX.I18N.EPCSupport.close = "Close";
ORYX.I18N.EPCSupport.error = "Error";
ORYX.I18N.EPCSupport.progressImp = "Import...";

if(!ORYX.I18N.ERDFSupport) ORYX.I18N.ERDFSupport = {};

ORYX.I18N.ERDFSupport.exp = "Export to ERDF";
ORYX.I18N.ERDFSupport.expDesc = "Export to ERDF";
ORYX.I18N.ERDFSupport.imp = "Import from ERDF";
ORYX.I18N.ERDFSupport.impDesc = "Import from ERDF";
ORYX.I18N.ERDFSupport.impFailed = "Request for import of ERDF failed.";
ORYX.I18N.ERDFSupport.impFailed2 = "An error while importing occurs! <br/>Please check error message: <br/><br/>";
ORYX.I18N.ERDFSupport.error = "Error";
ORYX.I18N.ERDFSupport.noCanvas = "The xml document has no Oryx canvas node included!";
ORYX.I18N.ERDFSupport.noSS = "The Oryx canvas node has no stencil set definition included!";
ORYX.I18N.ERDFSupport.wrongSS = "The given stencil set does not fit to the current editor!";
ORYX.I18N.ERDFSupport.selectFile = "Select an ERDF (.xml) file or type in the ERDF to import it!";
ORYX.I18N.ERDFSupport.file = "File";
ORYX.I18N.ERDFSupport.impERDF = "Import ERDF";
ORYX.I18N.ERDFSupport.impBtn = "Import";
ORYX.I18N.ERDFSupport.impProgress = "Importing...";
ORYX.I18N.ERDFSupport.close = "Close";
ORYX.I18N.ERDFSupport.deprTitle = "Really export to eRDF?";
ORYX.I18N.ERDFSupport.deprText = "Exporting to eRDF is not recommended anymore because the support will be stopped in future versions of the Oryx editor. If possible, export the model to JSON. Do you want to export anyway?";

if(!ORYX.I18N.jPDLSupport) ORYX.I18N.jPDLSupport = {};

ORYX.I18N.jPDLSupport.group = "ExecBPMN";
ORYX.I18N.jPDLSupport.exp = "Export to jPDL";
ORYX.I18N.jPDLSupport.expDesc = "Export to jPDL";
ORYX.I18N.jPDLSupport.imp = "Import from jPDL";
ORYX.I18N.jPDLSupport.impDesc = "Import jPDL File";
ORYX.I18N.jPDLSupport.impFailedReq = "Request for import of jPDL failed.";
ORYX.I18N.jPDLSupport.impFailedJson = "Transformation of jPDL failed.";
ORYX.I18N.jPDLSupport.impFailedJsonAbort = "Import aborted.";
ORYX.I18N.jPDLSupport.loadSseQuestionTitle = "jBPM stencil set extension needs to be loaded"; 
ORYX.I18N.jPDLSupport.loadSseQuestionBody = "In order to import jPDL, the stencil set extension has to be loaded. Do you want to proceed?";
ORYX.I18N.jPDLSupport.expFailedReq = "Request for export of model failed.";
ORYX.I18N.jPDLSupport.expFailedXml = "Export to jPDL failed. Exporter reported: ";
ORYX.I18N.jPDLSupport.error = "Error";
ORYX.I18N.jPDLSupport.selectFile = "Select an jPDL (.xml) file or type in the jPDL to import it!";
ORYX.I18N.jPDLSupport.file = "File";
ORYX.I18N.jPDLSupport.impJPDL = "Import jPDL";
ORYX.I18N.jPDLSupport.impBtn = "Import";
ORYX.I18N.jPDLSupport.impProgress = "Importing...";
ORYX.I18N.jPDLSupport.close = "Close";

if(!ORYX.I18N.Save) ORYX.I18N.Save = {};

ORYX.I18N.Save.group = "File";
ORYX.I18N.Save.save = "Save";
ORYX.I18N.Save.saveDesc = "保存";
ORYX.I18N.Save.saveAs = "Save As...";
ORYX.I18N.Save.saveAsDesc = "另存为...";
ORYX.I18N.Save.unsavedData = "数据未保存, 请在退出前保存数据, 否则您的修改将会丢失!";
ORYX.I18N.Save.newProcess = "新流程";
ORYX.I18N.Save.saveAsTitle = "另存为...";
ORYX.I18N.Save.saveBtn = "保存";
ORYX.I18N.Save.close = "关闭";
ORYX.I18N.Save.savedAs = "流程已保存";
ORYX.I18N.Save.saved = "流程已保存!";
ORYX.I18N.Save.failed = "保存失败.";
ORYX.I18N.Save.noRights = "您没有保存修改的权限.";
ORYX.I18N.Save.saving = "正在保存";
ORYX.I18N.Save.saveAsHint = "流程图存储在:";

if(!ORYX.I18N.File) ORYX.I18N.File = {};

ORYX.I18N.File.group = "File";
ORYX.I18N.File.print = "Print";
ORYX.I18N.File.printDesc = "Print current model";
ORYX.I18N.File.pdf = "Export as PDF";
ORYX.I18N.File.pdfDesc = "Export as PDF";
ORYX.I18N.File.info = "Info";
ORYX.I18N.File.infoDesc = "Info";
ORYX.I18N.File.genPDF = "Generating PDF";
ORYX.I18N.File.genPDFFailed = "Generating PDF failed.";
ORYX.I18N.File.printTitle = "Print";
ORYX.I18N.File.printMsg = "We are currently experiencing problems with the printing function. We recommend using the PDF Export to print the diagram. Do you really want to continue printing?";

if(!ORYX.I18N.Grouping) ORYX.I18N.Grouping = {};

ORYX.I18N.Grouping.grouping = "Grouping";
ORYX.I18N.Grouping.group = "Group";
ORYX.I18N.Grouping.groupDesc = "Groups all selected shapes";
ORYX.I18N.Grouping.ungroup = "Ungroup";
ORYX.I18N.Grouping.ungroupDesc = "Deletes the group of all selected Shapes";

if(!ORYX.I18N.Loading) ORYX.I18N.Loading = {};

ORYX.I18N.Loading.waiting ="请等待...";

if(!ORYX.I18N.PropertyWindow) ORYX.I18N.PropertyWindow = {};

ORYX.I18N.PropertyWindow.name = "属性名";
ORYX.I18N.PropertyWindow.value = "值";
ORYX.I18N.PropertyWindow.selected = "选择";
ORYX.I18N.PropertyWindow.clickIcon = "点击图标";
ORYX.I18N.PropertyWindow.add = "添加";
ORYX.I18N.PropertyWindow.rem = "删除";
ORYX.I18N.PropertyWindow.complex = "";
ORYX.I18N.PropertyWindow.text = "描述";
ORYX.I18N.PropertyWindow.ok = "确定";
ORYX.I18N.PropertyWindow.cancel = "取消";
ORYX.I18N.PropertyWindow.dateFormat = "时间格式m/d/y";

if(!ORYX.I18N.ShapeMenuPlugin) ORYX.I18N.ShapeMenuPlugin = {};

ORYX.I18N.ShapeMenuPlugin.drag = "拖动";
ORYX.I18N.ShapeMenuPlugin.clickDrag = "点击或拖动";
ORYX.I18N.ShapeMenuPlugin.morphMsg = "Morph shape";

if(!ORYX.I18N.SyntaxChecker) ORYX.I18N.SyntaxChecker = {};

ORYX.I18N.SyntaxChecker.group = "Verification";
ORYX.I18N.SyntaxChecker.name = "语法检查器";
ORYX.I18N.SyntaxChecker.desc = "语法检查器";
ORYX.I18N.SyntaxChecker.noErrors = "这些语法没有错误.";
ORYX.I18N.SyntaxChecker.invalid = "无效应答.";
ORYX.I18N.SyntaxChecker.checkingMessage = "正在检查...";

if(!ORYX.I18N.Deployer) ORYX.I18N.Deployer = {};

ORYX.I18N.Deployer.group = "发布";
ORYX.I18N.Deployer.name = "发布流程";
ORYX.I18N.Deployer.desc = "发布流程到引擎";

if(!ORYX.I18N.Undo) ORYX.I18N.Undo = {};

ORYX.I18N.Undo.group = "Undo";
ORYX.I18N.Undo.undo = "Undo";
ORYX.I18N.Undo.undoDesc = "撤销最后的操作";
ORYX.I18N.Undo.redo = "Redo";
ORYX.I18N.Undo.redoDesc = "重做最后的操作";

if(!ORYX.I18N.View) ORYX.I18N.View = {};

ORYX.I18N.View.group = "Zoom";
ORYX.I18N.View.zoomIn = "Zoom In";
ORYX.I18N.View.zoomInDesc = "放大流程图";
ORYX.I18N.View.zoomOut = "Zoom Out";
ORYX.I18N.View.zoomOutDesc = "缩小流程图";
ORYX.I18N.View.zoomStandard = "标准缩放";
ORYX.I18N.View.zoomStandardDesc = "缩放到标准级别";
ORYX.I18N.View.zoomFitToModel = "缩放到合适大小";
ORYX.I18N.View.zoomFitToModelDesc = "缩放到合适大小";

/** New Language Properties: 08.12.2008 */

ORYX.I18N.PropertyWindow.title = "属性";

if(!ORYX.I18N.ShapeRepository) ORYX.I18N.ShapeRepository = {};
ORYX.I18N.ShapeRepository.title = "工具箱";

ORYX.I18N.Save.dialogDesciption = "请输入名称, 描述和意见.";
ORYX.I18N.Save.dialogLabelTitle = "标题";
ORYX.I18N.Save.dialogLabelDesc = "描述";
ORYX.I18N.Save.dialogLabelType = "类型";
ORYX.I18N.Save.dialogLabelComment = "修改意见";

Ext.MessageBox.buttonText.yes = "是";
Ext.MessageBox.buttonText.no = "否";
Ext.MessageBox.buttonText.cancel = "取消";
Ext.MessageBox.buttonText.ok = "确定";

if(!ORYX.I18N.Perspective) ORYX.I18N.Perspective = {};
ORYX.I18N.Perspective.no = "不透视"
ORYX.I18N.Perspective.noTip = "未加载当前透视图"

/** New Language Properties: 21.04.2009 */
ORYX.I18N.JSONSupport = {
    imp: {
        name: "从JSON文件导入流程",
        desc: "从JSON文件导入流程",
        group: "导出",
        selectFile: "选择一个JSON文件并导入他!",
        file: "文件",
        btnImp: "导入",
        btnClose: "关闭",
        progress: "正在导入 ...",
        syntaxError: "语法错误"
    },
    exp: {
        name: "导出到JSON文件",
        desc: "导出当前流程到JSON文件",
        group: "导出"
    }
};

/** New Language Properties: 09.05.2009 */
if(!ORYX.I18N.JSONImport) ORYX.I18N.JSONImport = {};

ORYX.I18N.JSONImport.title = "JSON导入";
ORYX.I18N.JSONImport.wrongSS = "已导入的模板文件 ({0}) 与已经加载的模板不匹配({1})."

/** New Language Properties: 14.05.2009 */
if(!ORYX.I18N.RDFExport) ORYX.I18N.RDFExport = {};
ORYX.I18N.RDFExport.group = "导出";
ORYX.I18N.RDFExport.rdfExport = "导出为RDF";
ORYX.I18N.RDFExport.rdfExportDescription = "导出当前流程的RDF文件的xml定义文件";

/** New Language Properties: 15.05.2009*/
if(!ORYX.I18N.SyntaxChecker.BPMN) ORYX.I18N.SyntaxChecker.BPMN={};
ORYX.I18N.SyntaxChecker.BPMN_NO_SOURCE = "流必须有一个来源节点.";
ORYX.I18N.SyntaxChecker.BPMN_NO_TARGET = "流必须有一个目标节点.";
ORYX.I18N.SyntaxChecker.BPMN_DIFFERENT_PROCESS = "来源节点和目标节点必须在同一个流程中.";
ORYX.I18N.SyntaxChecker.BPMN_SAME_PROCESS = "来源节点和目标节点必须放在不同的甬道内.";
ORYX.I18N.SyntaxChecker.BPMN_FLOWOBJECT_NOT_CONTAINED_IN_PROCESS = "一个流对象只能包含在一个流程内.";
ORYX.I18N.SyntaxChecker.BPMN_ENDEVENT_WITHOUT_INCOMING_CONTROL_FLOW = "一个结束事件必须有一个输入的顺序流.";
ORYX.I18N.SyntaxChecker.BPMN_STARTEVENT_WITHOUT_OUTGOING_CONTROL_FLOW = "一个开事件点必须有一个输出的顺序流.";
ORYX.I18N.SyntaxChecker.BPMN_STARTEVENT_WITH_INCOMING_CONTROL_FLOW = "开始节点不能包含输入的顺序流.";
ORYX.I18N.SyntaxChecker.BPMN_ATTACHEDINTERMEDIATEEVENT_WITH_INCOMING_CONTROL_FLOW = "附属中间事件不能有输入的顺序流.";
ORYX.I18N.SyntaxChecker.BPMN_ATTACHEDINTERMEDIATEEVENT_WITHOUT_OUTGOING_CONTROL_FLOW = "附属中间事件必须有一个明确的输出顺序流.";
ORYX.I18N.SyntaxChecker.BPMN_ENDEVENT_WITH_OUTGOING_CONTROL_FLOW = "一个结束事件不能有输出顺序流.";
ORYX.I18N.SyntaxChecker.BPMN_EVENTBASEDGATEWAY_BADCONTINUATION = "事件网关不能连接一个其他网关或子流程.";
ORYX.I18N.SyntaxChecker.BPMN_NODE_NOT_ALLOWED = "不允许的节点类型.";

if(!ORYX.I18N.SyntaxChecker.IBPMN) ORYX.I18N.SyntaxChecker.IBPMN={};
ORYX.I18N.SyntaxChecker.IBPMN_NO_ROLE_SET = "发送人和接收人必须一起使用";
ORYX.I18N.SyntaxChecker.IBPMN_NO_INCOMING_SEQFLOW = "这个节点必须有一个输入的顺序流.";
ORYX.I18N.SyntaxChecker.IBPMN_NO_OUTGOING_SEQFLOW = "这个节点必须有一个输出的顺序流.";

if(!ORYX.I18N.SyntaxChecker.InteractionNet) ORYX.I18N.SyntaxChecker.InteractionNet={};
ORYX.I18N.SyntaxChecker.InteractionNet_SENDER_NOT_SET = "没有设置发送人";
ORYX.I18N.SyntaxChecker.InteractionNet_RECEIVER_NOT_SET = "没有设置接收人";
ORYX.I18N.SyntaxChecker.InteractionNet_MESSAGETYPE_NOT_SET = "没有设置消息类型";
ORYX.I18N.SyntaxChecker.InteractionNet_ROLE_NOT_SET = "没有设置角色";

if(!ORYX.I18N.SyntaxChecker.EPC) ORYX.I18N.SyntaxChecker.EPC={};
ORYX.I18N.SyntaxChecker.EPC_NO_SOURCE = "每一个边界须有一个来源节点.";
ORYX.I18N.SyntaxChecker.EPC_NO_TARGET = "每一个边界须有一个目标节点.";
ORYX.I18N.SyntaxChecker.EPC_NOT_CONNECTED = "节点必须被连接到一个边界";
ORYX.I18N.SyntaxChecker.EPC_NOT_CONNECTED_2 = "节点必须被连接到更多的边界.";
ORYX.I18N.SyntaxChecker.EPC_TOO_MANY_EDGES = "节点已经连接了太多的边界.";
ORYX.I18N.SyntaxChecker.EPC_NO_CORRECT_CONNECTOR = "节点不是一个正确的连接器.";
ORYX.I18N.SyntaxChecker.EPC_MANY_STARTS = "这个位置只能有一个连接器.";


ORYX.I18N.SyntaxChecker.EPC_FUNCTION_AFTER_OR = "在 或/异或 分隔后不能使用方法.";


ORYX.I18N.SyntaxChecker.EPC_PI_AFTER_OR = "在 或/异或 分隔后不能使用程序接口.";
ORYX.I18N.SyntaxChecker.EPC_FUNCTION_AFTER_FUNCTION =  "在一个方法后面不能紧接着使用另一个方法.";
ORYX.I18N.SyntaxChecker.EPC_EVENT_AFTER_EVENT =  "在一个事件的后面不能紧接着使用另外的事件.";
ORYX.I18N.SyntaxChecker.EPC_PI_AFTER_FUNCTION =  "在一个方法的后面不能紧接着使用程序接口.";
ORYX.I18N.SyntaxChecker.EPC_FUNCTION_AFTER_PI =  "在程序接口的后面不能紧接着使用方法.";
ORYX.I18N.SyntaxChecker.EPC_SOURCE_EQUALS_TARGET = "边界不能同时连接两个不同的节点."

if(!ORYX.I18N.SyntaxChecker.PetriNet) ORYX.I18N.SyntaxChecker.PetriNet={};

ORYX.I18N.SyntaxChecker.PetriNet_NOT_BIPARTITE = "图不是两部分";

ORYX.I18N.SyntaxChecker.PetriNet_NO_LABEL = "没有为一个标记变化设置标签";

ORYX.I18N.SyntaxChecker.PetriNet_NO_ID = "节点没有id";
ORYX.I18N.SyntaxChecker.PetriNet_SAME_SOURCE_AND_TARGET = "两个关系流有相同的来源和目标";
ORYX.I18N.SyntaxChecker.PetriNet_NODE_NOT_SET = "关系流没有设置连接的节点";

/** New Language Properties: 02.06.2009*/
ORYX.I18N.Edge = "边界";
ORYX.I18N.Node = "节点";

/** New Language Properties: 03.06.2009*/
ORYX.I18N.SyntaxChecker.notice = "鼠标移动到红叉上查看错误信息.";

/** New Language Properties: 05.06.2009*/
if(!ORYX.I18N.RESIZE) ORYX.I18N.RESIZE = {};
ORYX.I18N.RESIZE.tipGrow = "增加画布尺寸:";
ORYX.I18N.RESIZE.tipShrink = "减少画布尺寸:";
ORYX.I18N.RESIZE.N = "上";
ORYX.I18N.RESIZE.W = "左";
ORYX.I18N.RESIZE.S ="下";
ORYX.I18N.RESIZE.E ="右";

/** New Language Properties: 15.07.2009*/
if(!ORYX.I18N.Layouting) ORYX.I18N.Layouting ={};
ORYX.I18N.Layouting.doing = "正在布局...";

/** New Language Properties: 18.08.2009*/
ORYX.I18N.SyntaxChecker.MULT_ERRORS = "多个错误";

/** New Language Properties: 08.09.2009*/
if(!ORYX.I18N.PropertyWindow) ORYX.I18N.PropertyWindow = {};
ORYX.I18N.PropertyWindow.oftenUsed = "经常使用";
ORYX.I18N.PropertyWindow.moreProps = "更多属性";

/** New Language Properties 01.10.2009 */
if(!ORYX.I18N.SyntaxChecker.BPMN2) ORYX.I18N.SyntaxChecker.BPMN2 = {};

ORYX.I18N.SyntaxChecker.BPMN2_DATA_INPUT_WITH_INCOMING_DATA_ASSOCIATION = "数据输入必须没有任何输入数据关联.";


ORYX.I18N.SyntaxChecker.BPMN2_DATA_OUTPUT_WITH_OUTGOING_DATA_ASSOCIATION = "数据输出必须没有任何输出数据关联.";


ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_TARGET_WITH_TOO_MANY_INCOMING_SEQUENCE_FLOWS = "事件网关的目标节点只能有一个输入的顺序流.";

/** New Language Properties 02.10.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WITH_TOO_LESS_OUTGOING_SEQUENCE_FLOWS = "任何一个事件网关必须有两个以上的输出顺序流.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_EVENT_TARGET_CONTRADICTION = "如果配置了一个中间消息捕获事件, 那么也必须配置接受任务，反之亦然.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WRONG_TRIGGER = "中间捕获事件的有效触发条件包含: 消息, 型号, 定时器, 多条件.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WRONG_CONDITION_EXPRESSION = "事件网关的输出顺序流必须有一个条件表达式.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_NOT_INSTANTIATING = "这个网关不能满足流程实例的运行条件. 请使用开始时间或配置网关的实例属性.";

/** New Language Properties 05.10.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAYDIRECTION_MIXED_FAILURE = "网关必须有两个以上的输入和输出顺序流.";
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAYDIRECTION_CONVERGING_FAILURE = "这个网关必须有多个输入顺序流但是不能有多个输出顺序流.";
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAYDIRECTION_DIVERGING_FAILURE = "这个网关不能有多个输入顺序流但是必须有多个输出顺序流.";
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAY_WITH_NO_OUTGOING_SEQUENCE_FLOW = "一个网关最少有一个输出顺序流.";
ORYX.I18N.SyntaxChecker.BPMN2_RECEIVE_TASK_WITH_ATTACHED_EVENT = "接收任务中使用事件网关配置必须没有任何附加的中间事件.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_SUBPROCESS_BAD_CONNECTION = "一个事件子流程必须没有任何输入或输出流.";

/** New Language Properties 13.10.2009 */
ORYX.I18N.SyntaxChecker.BPMN_MESSAGE_FLOW_NOT_CONNECTED = "至少消息流的一边已经被连接.";

/** New Language Properties 24.11.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_TOO_MANY_INITIATING_MESSAGES = "一个编排活动可能只有一个初始化消息.";
ORYX.I18N.SyntaxChecker.BPMN_MESSAGE_FLOW_NOT_ALLOWED = "此处不允许设置一个消息流.";

/** New Language Properties 27.11.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WITH_TOO_LESS_INCOMING_SEQUENCE_FLOWS = "一个基于事件的没有实例化的网关必须有至少一个输入流.";
ORYX.I18N.SyntaxChecker.BPMN2_TOO_FEW_INITIATING_PARTICIPANTS = "一个编排活动必须有一个初始参与者 (white).";
ORYX.I18N.SyntaxChecker.BPMN2_TOO_MANY_INITIATING_PARTICIPANTS = "一个编排活动不能有多个初始化参与者 (white)."

ORYX.I18N.SyntaxChecker.COMMUNICATION_AT_LEAST_TWO_PARTICIPANTS = "至少两个参与者才能沟通.";
ORYX.I18N.SyntaxChecker.MESSAGEFLOW_START_MUST_BE_PARTICIPANT = "消息流的来源必须是一个参与者.";
ORYX.I18N.SyntaxChecker.MESSAGEFLOW_END_MUST_BE_PARTICIPANT = "消息流的目标必须是一个参与者.";
ORYX.I18N.SyntaxChecker.CONV_LINK_CANNOT_CONNECT_CONV_NODES = "会话链接必须连接一个沟通或者子节点和一个参与者进行会话.";
