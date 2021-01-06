function like(blogId,ifInit)
{

    var xmlhttp;
    if (window.XMLHttpRequest)
    {
        // IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        xmlhttp=new XMLHttpRequest();
    }
    else
    {
        // IE6, IE5 浏览器执行代码
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            document.getElementById("likesnum").innerHTML=xmlhttp.responseText;
        }
    }
    xmlhttp.open("GET","/likeBlog?blogId="+blogId+"&&"+"ifInit="+ifInit,true);
    xmlhttp.send();
}
function likeCommit(commitId,ifInit)//有bug
{

    var xmlhttp;
    if (window.XMLHttpRequest)
    {
        // IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
        xmlhttp=new XMLHttpRequest();
    }
    else
    {
        // IE6, IE5 浏览器执行代码
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            document.getElementById("commitlikesnum"+commitId).innerHTML=xmlhttp.responseText;
        }
    }
    xmlhttp.open("GET","/likeCommit?commitId="+commitId+"&&"+"ifInit="+ifInit,true);
    xmlhttp.send();
}


