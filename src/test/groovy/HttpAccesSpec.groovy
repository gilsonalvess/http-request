import spock.lang.Specification

class HttpAccesSpec extends Specification {

    void 'testa resultado requisicao'() {
        setup:
        HttpAcces httpAcces = new HttpAcces()
        String url = 'http://localhost:8080/hello'

        when:
        List resultado = httpAcces.sendRequest(url, 'GET')

        then:
        resultado
    }
}
