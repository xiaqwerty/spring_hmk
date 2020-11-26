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