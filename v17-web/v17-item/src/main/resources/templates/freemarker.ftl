<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    hello,${username}
    <hr />
    商品名称：${product.name}
    价格：${product.price}
    创建时间：${product.createTime?date}
    创建时间：${product.createTime?time}
    创建时间：${product.createTime?datetime}
    <hr/>
    msg：${msg!}
    msg：${msg!"暂无消息"}
    <#if msg??>
        when-present
        <#else>
        when-missing
    </#if>
    <h5>展示集合信息</h5>
    <#list list as pro>
        序号：${pro_index}
        商品名：${pro.name}
        价格：${pro.price}
        创建时间：${pro.createTime?datetime}
    </#list>
    <hr/>
    <h5>逻辑判断</h5>
    <#if (age > 40)>
        中年
        <#elseif (age > 30)>
        青年
        <#else>
        少年
    </#if>
</body>
</html>