import spock.lang.Specification

class HttpAccesSpec extends Specification {

    void 'testa resultado requisicao'() {
        setup:
        HttpAcces httpAcces = new HttpAcces()
        String url1 = 'http://edoc.agirgo.org.br:5000/GerenciadorProcessoWeb/index.xhtml'
        String url2 = 'http://edoc.agirgo.org.br:5000/GerenciadorProcessoWeb/processo/dashboard.xhtml'

        when:
        List pageIndex = httpAcces.sendRequest(url1, 'GET')
        String viewState = pageIndex.get(1).find('(?<=value=")[\\d-:]+(?=" autocomplete)')
        Map<String, Object> params = [
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

        List login = httpAcces.sendRequest(url1, 'POST', params)
        List paginaInicial = httpAcces.sendRequest(url2, 'GET')

        then:
        paginaInicial
        pageIndex
    }
}
