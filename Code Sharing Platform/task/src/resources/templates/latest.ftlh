
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Latest</title>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
    <#list latest_list?sort_by("date")?reverse as snippet>
        <span>
            ${snippet.date}
        </span>
        <pre>
            <code>
                ${snippet.code}
            </code>
        </pre>
    </#list>
</body>
</html>