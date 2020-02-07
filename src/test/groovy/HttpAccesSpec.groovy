import spock.lang.Specification

class HttpAccesSpec extends Specification {

    void 'testa resultado requisicao'() {
        setup:
        HttpAcces httpAcces = new HttpAcces()
        String url = 'http://localhost:8080/login'

        when:
        List login = httpAcces.sendRequest(url, 'GET')
        String token = login.get(1).find('(?<=name="_csrf" type="hidden" value=")[\\w-]+')
        Map<String, Object> params = [
                'username': 'admin',
                'password': 'admin',
                '_csrf'   : token
        ]
        List paginaInicial = httpAcces.sendRequest(url, 'POST', params)

        then:
        paginaInicial
        login
    }
}
