<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Code</title>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
    <span>
        <pre id="code_snippet">
            <code>
                ${code_list.code}
            </code>
        </pre>
        <span id="load_date">
            ${code_list.date}
        </span>
        <#if code_list.isViewRestricted() == true>
            <p><span id="views_restriction">${code_list.getViews()} more views allowed</span></p>
        </#if>
        <#if code_list.isTimeRestricted() == true>
            <p><span id="time_restriction">The code will be available for ${code_list.getTime()} seconds</span></p>
        </#if>

    </span>
</body>
</html>