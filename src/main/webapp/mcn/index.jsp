<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="com.jumei.mcn.common.DoveConfigUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String domian = DoveConfigUtils.getShuabaBaseMcnH5Config("jmstatic_url_https");

    String client_fv = request.getParameter("fv"); //传递参数,强刷jmstatic_url_finance_https
    String client_v = DoveConfigUtils.getShuabaBaseMcnH5Config("h5-version");
    String title = DoveConfigUtils.getShuabaBaseMcnH5Config("manager-title");
    if(StringUtils.isNotBlank(client_fv)){
        client_v =client_fv;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title><%=title%></title><link rel="shortcut icon" href="http://tf.shuabaola.cn/favicon.ico">
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet"  href="<%=domian%>/finace/shuabao-mcn-admin/dist/<%=client_v%>/css/app.css">
</head>
<body>
<div id="app"></div>
<script src="<%=domian%>/finace/shuabao-mcn-admin/dist/<%=client_v%>/js/manifest.js"></script>
<script src="<%=domian%>/finace/shuabao-mcn-admin/dist/<%=client_v%>/js/vendor.js"></script>
<script src="<%=domian%>/finace/shuabao-mcn-admin/dist/<%=client_v%>/js/app.js"></script>
</body>
</html>