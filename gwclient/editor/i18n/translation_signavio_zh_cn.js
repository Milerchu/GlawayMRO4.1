ORYX.I18N.PropertyWindow.dateFormat = "d/m/y";

ORYX.I18N.View.East = "属性";
ORYX.I18N.View.West = "建模元素";

ORYX.I18N.Oryx.title	= "Signavio";
ORYX.I18N.Oryx.pleaseWait = "正在加载Signavio设计器...";
ORYX.I18N.Edit.cutDesc = "剪切选择的内容到剪贴板";
ORYX.I18N.Edit.copyDesc = "复制选择的内容到剪贴板";
ORYX.I18N.Edit.pasteDesc = "粘贴内容到设计器画布";
ORYX.I18N.ERDFSupport.noCanvas = "文档中包含不可识别节点!";
ORYX.I18N.ERDFSupport.noSS = "Signavio设计器画布节点没有模版定义!";
ORYX.I18N.ERDFSupport.deprText = "导出ePDF不再推荐使用因为在将来版本中将不再支持.如果可以,可导出流程生成JSON. 你还要导出吗?";
ORYX.I18N.Save.pleaseWait = "请稍等<br/>正在保存...";

ORYX.I18N.Save.saveAs = "保存副本...";
ORYX.I18N.Save.saveAsDesc = "保存副本...";
ORYX.I18N.Save.saveAsTitle = "保存副本...";
ORYX.I18N.Save.savedAs = "复制保存";
ORYX.I18N.Save.savedDescription = "流程图已存储";
ORYX.I18N.Save.notAuthorized = "你目前尚未登录，登录之后才可保存流程图."
ORYX.I18N.Save.transAborted = "保存时间过长. 你可以选择更快的连接. 如果你使用无线局域网,请检测你的网络连接情况.";
ORYX.I18N.Save.noRights = "你无法存储流程. 请检测<a href='/p/explorer' target='_blank'>Signavio Explorer</a>, 如果你仍然拥有操作权限.";
ORYX.I18N.Save.comFailed = "无法连接 Signavio服务器.请检测你的网络连接情况.如果问题无法仍然存在，可通过工具栏中的信封标志联系Signavio获得支持.";
ORYX.I18N.Save.failed = "当保存流程失败，可以尝试几次.如果问题仍然存在，可通过工具栏中的信封标志联系Signavio获得支持.";
ORYX.I18N.Save.exception = "当保存流程出错，可以尝试几次.如果问题仍然存在，可通过工具栏中的信封标志联系Signavio获得支持.";
ORYX.I18N.Save.retrieveData = "请稍等, 数据正在检索.";

/** New Language Properties: 10.6.09*/
if(!ORYX.I18N.ShapeMenuPlugin) ORYX.I18N.ShapeMenuPlugin = {};
ORYX.I18N.ShapeMenuPlugin.morphMsg = "更换节点类型";
ORYX.I18N.ShapeMenuPlugin.morphWarningTitleMsg = "更换节点类型";
ORYX.I18N.ShapeMenuPlugin.morphWarningMsg = "在转换元素中不包含子形状.<br/>你还要转换吗?";

if (!Signavio) { var Signavio = {}; }
if (!Signavio.I18N) { Signavio.I18N = {} }
if (!Signavio.I18N.Editor) { Signavio.I18N.Editor = {} }

if (!Signavio.I18N.Editor.Linking) { Signavio.I18N.Editor.Linking = {} }
Signavio.I18N.Editor.Linking.CreateDiagram = "创建一个新的图表";
Signavio.I18N.Editor.Linking.UseDiagram = "使用已存在的图表";
Signavio.I18N.Editor.Linking.UseLink = "使用web链接";
Signavio.I18N.Editor.Linking.Close = "关闭";
Signavio.I18N.Editor.Linking.Cancel = "取消";
Signavio.I18N.Editor.Linking.UseName = "采用图名";
Signavio.I18N.Editor.Linking.UseNameHint = "用引用图表的名称替换流程元素的当前名称.";
Signavio.I18N.Editor.Linking.CreateTitle = "建立链接";
Signavio.I18N.Editor.Linking.AlertSelectModel = "你需要选择一个流程.";
Signavio.I18N.Editor.Linking.ButtonLink = "链接图表";
Signavio.I18N.Editor.Linking.LinkNoAccess = "你无法访问这个图表.";
Signavio.I18N.Editor.Linking.LinkUnavailable = "此图不可用.";
Signavio.I18N.Editor.Linking.RemoveLink = "删除链接";
Signavio.I18N.Editor.Linking.EditLink = "编辑链接";
Signavio.I18N.Editor.Linking.OpenLink = "打开";
Signavio.I18N.Editor.Linking.BrokenLink = "链接无法打开!";
Signavio.I18N.Editor.Linking.PreviewTitle = "预览";

if(!Signavio.I18N.Glossary_Support) { Signavio.I18N.Glossary_Support = {}; }
Signavio.I18N.Glossary_Support.renameEmpty = "没有字典条目";
Signavio.I18N.Glossary_Support.renameLoading = "搜索...";

/** New Language Properties: 08.09.2009*/
if(!ORYX.I18N.PropertyWindow) ORYX.I18N.PropertyWindow = {};
ORYX.I18N.PropertyWindow.oftenUsed = "主要特性";
ORYX.I18N.PropertyWindow.moreProps = "更多特性";

ORYX.I18N.PropertyWindow.btnOpen = "打开";
ORYX.I18N.PropertyWindow.btnRemove = "删除";
ORYX.I18N.PropertyWindow.btnEdit = "编辑";
ORYX.I18N.PropertyWindow.btnUp = "上移";
ORYX.I18N.PropertyWindow.btnDown = "下移";
ORYX.I18N.PropertyWindow.createNew = "新建";

if(!ORYX.I18N.PropertyWindow) ORYX.I18N.PropertyWindow = {};
ORYX.I18N.PropertyWindow.oftenUsed = "主要属性";
ORYX.I18N.PropertyWindow.moreProps = "更多属性";
ORYX.I18N.PropertyWindow.characteristicNr = "成本 &amp; 资源分析";
ORYX.I18N.PropertyWindow.meta = "自定义属性";

if(!ORYX.I18N.PropertyWindow.Category){ORYX.I18N.PropertyWindow.Category = {}}
ORYX.I18N.PropertyWindow.Category.popular = "主要属性";
ORYX.I18N.PropertyWindow.Category.characteristicnr = "成本 &amp; 资源分析";
ORYX.I18N.PropertyWindow.Category.others = "更多属性";
ORYX.I18N.PropertyWindow.Category.meta = "自定义属性";

if(!ORYX.I18N.PropertyWindow.ListView) ORYX.I18N.PropertyWindow.ListView = {};
ORYX.I18N.PropertyWindow.ListView.title = "编辑: ";
ORYX.I18N.PropertyWindow.ListView.dataViewLabel = "已经存在条目.";
ORYX.I18N.PropertyWindow.ListView.dataViewEmptyText = "没有条目列表.";
ORYX.I18N.PropertyWindow.ListView.addEntryLabel = "新增一个新的条目";
ORYX.I18N.PropertyWindow.ListView.buttonAdd = "新增";
ORYX.I18N.PropertyWindow.ListView.save = "保存";
ORYX.I18N.PropertyWindow.ListView.cancel = "取消";

if(!Signavio.I18N.Buttons) Signavio.I18N.Buttons = {};
Signavio.I18N.Buttons.save		= "保存";
Signavio.I18N.Buttons.cancel 	= "取消";
Signavio.I18N.Buttons.remove	= "删除";

if(!Signavio.I18N.btn) {Signavio.I18N.btn = {};}
Signavio.I18N.btn.btnEdit = "编辑";
Signavio.I18N.btn.btnRemove = "删除";
Signavio.I18N.btn.moveUp = "上移";
Signavio.I18N.btn.moveDown = "下移";

if(!Signavio.I18N.field) {Signavio.I18N.field = {};}
Signavio.I18N.field.Url = "URL";
Signavio.I18N.field.UrlLabel = "标签";
