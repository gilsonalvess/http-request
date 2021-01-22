import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import spock.lang.Specification

class HttpAccesSpec extends Specification {

    void 'testa resultado requisicao'() {
        setup:
        HttpAcces httpAcces = new HttpAcces()
        String url1 = 'http://edoc.agirgo.org.br:5000/GerenciadorProcessoWeb/index.xhtml'
        String url2 = 'http://edoc.agirgo.org.br:5000/GerenciadorProcessoWeb/processo/dashboard.xhtml'

        when:
        String pageIndex = httpAcces.sendRequest(url1, "GET")
        String viewState = pageIndex.find('(?<=value=")[\\d-:]+(?=" autocomplete)')
        Map params = [
                'javax.faces.partial.ajax'   : true,
                'javax.faces.source'         : 'formLogin:j_idt20',
                'javax.faces.partial.execute': '@all',
                'javax.faces.partial.render' : 'formLogin',
                'formLogin:j_idt20'          : 'formLogin:j_idt20',
                'formLogin'                  : 'formLogin',
                'formLogin:j_idt16'          : '10302',
                'formLogin:j_idt18'          : 'obdI109j',
                'javax.faces.ViewState'      : viewState
        ]

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, "formLogin=formLogin&formLogin:j_idt16=10302&formLogin:j_idt18=obdI109j&formLogin:j_idt20=formLogin:j_idt20&javax.faces.ViewState=${viewState}&javax.faces.partial.ajax=true&javax.faces.partial.execute=@all&javax.faces.partial.render=formLogin&javax.faces.source=formLogin:j_idt20");
        httpAcces.sendRequest(url1, 'POST', body)
        String paginaInicial = httpAcces.sendRequest(url2, 'GET')

        then:
        paginaInicial
        pageIndex
    }
}
