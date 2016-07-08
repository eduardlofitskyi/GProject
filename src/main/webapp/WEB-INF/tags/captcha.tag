<%@ tag import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ tag import="net.tanesha.recaptcha.ReCaptchaFactory" %>

<script type="text/javascript">var RecaptchaOptions = {theme: 'clean'};</script>
<%
    ReCaptcha reCaptcha = ReCaptchaFactory.newReCaptcha("6LcrNiQTAAAAALVwnMRwwMjHQ2oG43EFglZnVQMQ", "6LcrNiQTAAAAAPqlUXKd74DI_OFhPr1PryF_Fbgl", false);
    out.print(reCaptcha.createRecaptchaHtml(null, null));
%>