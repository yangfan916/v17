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
</body>
</html>