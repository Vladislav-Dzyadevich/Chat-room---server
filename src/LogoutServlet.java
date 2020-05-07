import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final UserList userList = UserList.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] body = requestBodyToArray(request);
        String json = new String(body, StandardCharsets.UTF_8);

        User user = User.fromJSON(json);
        if (user != null) {
            if (userList.isUserPresentInDatabase(user.getLogin(), user.getPassword())) {
                response.setStatus(HttpServletResponse.SC_OK);
                userList.logout(user);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
    private byte[] requestBodyToArray(HttpServletRequest req) throws IOException {
        InputStream is = req.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }


}
