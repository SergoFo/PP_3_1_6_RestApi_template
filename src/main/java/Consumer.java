import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Consumer {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://94.198.50.185:7081/api/users";

        // Создание заголовков запроса
        HttpHeaders headers = new HttpHeaders();

        // Выполнение первого запроса и получение заголовков ответа
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        // Получение Session ID из заголовков ответа
        String sessionId = responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        System.out.println("Session ID: " + sessionId);

        // Добавление Session ID в заголовки следующего запроса
        headers.set(HttpHeaders.COOKIE, sessionId);

        // Сохранение пользователя с id = 3
        String newUser = "{\"id\":3,\"name\":\"James\",\"lastName\":\"Brown\",\"age\":42}";
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> saveUserEntity = new HttpEntity<>(newUser, headers);

        ResponseEntity<String> saveUserResponse = restTemplate.exchange(url, HttpMethod.POST, saveUserEntity, String.class);
        System.out.println("Save User Response: " + saveUserResponse.getBody());

        // Изменение пользователя с id = 3
        String updatedUser = "{\"id\":3,\"name\":\"Thomas\",\"lastName\":\"Shelby\",\"age\":42}";
        HttpEntity<String> updateUserEntity = new HttpEntity<>(updatedUser, headers);

        ResponseEntity<String> updateUserResponse = restTemplate.exchange(url, HttpMethod.PUT, updateUserEntity, String.class);
        System.out.println("Update User Response: " + updateUserResponse.getBody());

        // Удаление пользователя с id = 3
        String deleteUserUrl = url + "/3";
        HttpEntity<String> deleteUserEntity = new HttpEntity<>(headers);

        ResponseEntity<String> deleteUserResponse = restTemplate.exchange(deleteUserUrl, HttpMethod.DELETE, deleteUserEntity, String.class);
        System.out.println("Delete User Response: " + deleteUserResponse.getBody());
    }
}
