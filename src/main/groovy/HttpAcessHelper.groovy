import okhttp3.MediaType
import okhttp3.RequestBody

class HttpAcessHelper {

    public static final String URL_PAGINA_LOGIN_OPA = 'http://localhost:8080/WebCrer_war/login.faces'
    public static final String URL_PAGINA_INICIAL_OPA = 'http://localhost:8080/WebCrer_war/index_logado.faces'

    public static final String URL_PAGINA_LOGIN_EDOC = 'http://localhost:8080/GerenciadorProcessoWeb-0.0.1-SNAPSHOT/index.xhtml'
    public static final String URL_PAGINA_INICIAL_EDOC = 'http://localhost:8080/GerenciadorProcessoWeb-0.0.1-SNAPSHOT/processo/dashboard.xhtml'

    public static final String URL_PAGINA_LOGIN_SIGEPI = 'http://produtividademedica.agirgo.org.br:9876/agirSaudeWeb/index.xhtml'
    public static final String URL_PAGINA_INICIAL_SIGEPI = 'http://produtividademedica.agirgo.org.br:9876/agirSaudeWeb/principal/dashboard.xhtml'


    static Map<String, Object> obtenhaParamsLoginEdoc(DadoLogin dadoLogin, String viewState) {
        Map<String, Object> params = [
                'javax.faces.partial.ajax'   : true,
                'javax.faces.source'         : 'formLogin:j_idt20',
                'javax.faces.partial.execute': '@all',
                'javax.faces.partial.render' : 'formLogin',
                'formLogin:j_idt20'          : 'formLogin:j_idt20',
                'formLogin'                  : 'formLogin',
                'formLogin:j_idt16'          : dadoLogin.login,
                'formLogin:j_idt18'          : dadoLogin.senha,
                'javax.faces.ViewState'      : viewState
        ] as Map<String, Object>
        return params
    }

    static Map<String, Object> obtenhaParamsLoginSigepi(DadoLogin dadoLogin, String viewState) {
        Map<String, Object> params = [
                'javax.faces.partial.ajax'   : "true",
                'javax.faces.source'         : "formLogin:j_idt19",
                'javax.faces.partial.execute': "@all",
                'javax.faces.partial.render' : "formLogin",
                'formLogin:j_idt19'          : "formLogin:j_idt19",
                'formLogin'                  : "formLogin",
                'javax.faces.ViewState'      : viewState,
                'formLogin:j_idt15'          : dadoLogin.login,
                'formLogin:j_idt17'          : dadoLogin.senha,
        ] as Map<String, Object>
        return params
    }

    static Map<String, Object> obtenhaParamsLoginOpa(DadoLogin dadoLogin) {
        Map<String, Object> params = [
                'j_id_jsp_113269692_2'                       : 'j_id_jsp_113269692_2',
                'j_id_jsp_113269692_2%3Aj_id_jsp_113269692_4': dadoLogin.login,
                'j_id_jsp_113269692_2%3Aj_id_jsp_113269692_6': dadoLogin.senha,
                'j_id_jsp_113269692_2%3Aj_id_jsp_113269692_7': 'Login',
                'javax.faces.ViewState'                      : 'j_id1'
        ] as Map<String, Object>
        return params
    }

    static RequestBody obtenhaRequestBody(Map params) {
        StringBuilder builder = new StringBuilder()
        params.each { k, v ->
            builder.append(k)
            builder.append("=")
            builder.append(v)
            builder.append("&")
        }

        String queryString = builder.toString().replaceAll('&\$', '')
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8")

        return RequestBody.create(mediaType, queryString)
    }

    static List<DadoLogin> obtenhaDadosLogin() {
        String conteudoArquivo = new File("src/main/resources/dadosLogin.txt").text
        String regexDadosLogin = '(?m)^([^\\|]+)\\|([^\\|]+)\\|([^\\|]+\$)'
        List<DadoLogin> dadosLogin = []

        conteudoArquivo.findAll(regexDadosLogin) { String linha, String login, String senha, String nome ->
            dadosLogin.add(new DadoLogin(login, senha, nome))
        }
        return dadosLogin
    }

}
