package com.yawtseb.bestway.util.email

class AuthenticationTemplate(){
    companion object{
        fun koreanTemplate(url: String):String{
            return "<!DOCTYPE html>\n<html>\n<head>\n<meta charset=\"UTF-8\">\n<title>Email Authentication</title>\n</head>\n<body>\n\n<div style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 540px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">\n\t<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">\n\t\t<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">BestWay</span><br />\n\t\t<span style=\"color: #02b875;\">메일인증</span> 안내입니다.\n\t</h1>\n\t<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">\n\t\t안녕하세요.<br />\n\t\tBestWay에 가입해 주셔서 진심으로 감사드립니다.<br />\n\t\t아래 <b style=\"color: #02b875;\">'메일 인증'</b> 버튼을 클릭하여 회원가입을 완료해 주세요.<br />\n\t\t감사합니다.\n\t</p>\n\n\t<a style=\"color: #FFF; text-decoration: none; text-align: center;\" href=\"$url\" target=\"_blank\"><p style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #02b875; line-height: 45px; vertical-align: middle; font-size: 16px;\">메일 인증</p></a>\n\n\t<div style=\"border-top: 1px solid #DDD; padding: 5px;\">\n\t\t<p style=\"font-size: 13px; line-height: 21px; color: #555;\">\n\t\t\t만약 버튼이 정상적으로 클릭되지 않는다면, 아래 링크를 복사하여 접속해 주세요.<br />\n\t\t\t$url\n\t\t</p>\n\t</div>\n</div>\n</body>\n</html>"
        }
    }
}