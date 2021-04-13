import okhttp3.RequestBody
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HttpAccesSpec extends Specification {

    static Integer qtdUsuariosLogados = 0

    void 'testa login varios usuarios - opa'() {
        setup:
        List<DadoLogin> dadosLoginList = HttpAcessHelper.obtenhaDadosLogin()

        when:
        for (DadoLogin dadoLogin in dadosLoginList) {
            realizarLoginOpa(dadoLogin)
        }
        then:
        println("Total de usuários logados: " + qtdUsuariosLogados)
    }

    void 'testa login varios usuarios - edoc'() {
        setup:
        List<DadoLogin> dadosLoginList = HttpAcessHelper.obtenhaDadosLogin().subList(0, 500)

        when:
        for (DadoLogin dadoLogin in dadosLoginList) {
            sleep(1500)
            realizarLoginEdoc(dadoLogin)
        }

        then:
        println("Total de usuários logados: " + qtdUsuariosLogados)
    }

    void 'testa login varios usuarios - sigepi'() {

        setup:
        DadoLogin dadosLoginList = HttpAcessHelper.obtenhaDadosLogin().find { it.login == "10302" }

        when:
        realizarLoginSigepi(dadosLoginList)

        then:
        println("Total de usuários logados: " + qtdUsuariosLogados)
    }

    private static realizarLoginOpa(DadoLogin dadoLogin) {
        HttpAcces httpAcces = new HttpAcces()
        String pageIndex = httpAcces.processaRequisicao(HttpAcessHelper.URL_PAGINA_LOGIN_OPA, "GET")
        String sessionid = pageIndex.find('jsessionid=.*(?=" enctype)').toUpperCase()
        Map<String, Object> params = HttpAcessHelper.obtenhaParamsLoginOpa(dadoLogin)

        RequestBody body = HttpAcessHelper.obtenhaRequestBody(params)
        httpAcces.processaRequisicao(HttpAcessHelper.URL_PAGINA_LOGIN_OPA, 'POST', body, sessionid)
        String paginaInicial = httpAcces.processaRequisicao(HttpAcessHelper.URL_PAGINA_INICIAL_OPA, 'GET', null, sessionid)
        Boolean loginSucesso = paginaInicial.find('Sejam Bem Vindos ao OPA!') asBoolean()

        if (loginSucesso) {
            println("O usuário ${dadoLogin.nome} logou no sistema OPA com sucesso em ${LocalDateTime.now()}")
            qtdUsuariosLogados++
        }
    }

    private static realizarLoginEdoc(DadoLogin dadoLogin) {
        HttpAcces httpAcces = new HttpAcces()
        String pageIndex = httpAcces.processaRequisicao(HttpAcessHelper.URL_PAGINA_LOGIN_EDOC, "GET")
        String sessionid = "JSESSIONID=".concat(pageIndex.find('(?<=jsessionid=).*(?=" enctype)'))
        String viewState = pageIndex.find('(?<=value=")[\\d-:]+(?=" autocomplete)')
        Map<String, Object> params = HttpAcessHelper.obtenhaParamsLoginEdoc(dadoLogin, viewState)

        RequestBody body = HttpAcessHelper.obtenhaRequestBody(params)
        httpAcces.processaRequisicao(HttpAcessHelper.URL_PAGINA_LOGIN_EDOC, 'POST', body, sessionid)
        String paginaInicial = httpAcces.processaRequisicao(HttpAcessHelper.URL_PAGINA_INICIAL_EDOC, 'GET', null, sessionid)
        Boolean loginSucesso = paginaInicial.find("Login: ${dadoLogin.login} - ${dadoLogin.nome}") asBoolean()

        if (loginSucesso) {
            println("${qtdUsuariosLogados} - O usuário ${dadoLogin.nome} logou no sistema E-doc com sucesso as ${LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))}")
            qtdUsuariosLogados++
        }
    }

    private static realizarLoginSigepi(DadoLogin dadoLogin) {
        HttpAcces httpAcces = new HttpAcces()
        String pageIndex = httpAcces.processaRequisicao(HttpAcessHelper.URL_PAGINA_LOGIN_SIGEPI, "GET")
        String sessionid = "JSESSIONID=".concat(pageIndex.find('(?<=jsessionid=).*(?=" enctype)'))
        String viewState = pageIndex.find('(?<=value=")[\\d-:]+(?=" autocomplete)')
        Map<String, Object> params = HttpAcessHelper.obtenhaParamsLoginSigepi(dadoLogin, viewState)

        RequestBody body = HttpAcessHelper.obtenhaRequestBody(params)
        httpAcces.processaRequisicao(HttpAcessHelper.URL_PAGINA_LOGIN_SIGEPI, 'POST', body, sessionid)
        String paginaInicial = httpAcces.processaRequisicao(HttpAcessHelper.URL_PAGINA_INICIAL_SIGEPI, 'GET', null, sessionid)
        Boolean loginSucesso = paginaInicial.find("Login: ${dadoLogin.login} - ${dadoLogin.nome}") asBoolean()

        if (loginSucesso) {
            println("${qtdUsuariosLogados} - O usuário ${dadoLogin.nome} logou no sistema SIGEPI com sucesso as ${LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))}")
            qtdUsuariosLogados++
        }
    }
}
