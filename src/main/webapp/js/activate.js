

//单击发送邮件激活账户

function Sendmail() {
    alert("发送成功！")
    $.post("/user.do?method=sendMail",function () {

        alert("发送成功！请前往邮箱认证")
        //跳转至主界面
        location.href="/html/frontpage/homepage.html"

    },"json")


}