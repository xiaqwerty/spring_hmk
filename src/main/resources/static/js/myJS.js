function checkInput()
{
    var input=document.getElementById("email").value;
    var myReg=/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/;
    /*alert("邮箱格式错误！");*/
    if(!myReg.test(input))
    {
        alert("邮箱格式错误！");
    }
}
function gotoMyAddressBook()
{
    window.location.href="/myAddressBook";
}
function gotoAddPerson()
{
    window.location.href="/addPerson";
}
function Post()
{
    document.myBox.action="/addPerson/add";
    document.myBox.submit();
}
function Cancel()
{
    document.myBox.action="/addPerson/cancel";
    document.myBox.submit();
}
function duplicatePhoneNumber(str)
{
    var xmlhttp;
    if (str.length==0)
    {
        document.getElementById("hint").innerHTML="";
        return;
    }
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
            document.getElementById("hint").innerHTML=xmlhttp.responseText;
        }
    }
    xmlhttp.open("GET","/addPerson/ifDuplicate?num="+str,true);
    xmlhttp.send();
}