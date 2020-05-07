import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class GettingUsersServlet extends HttpServlet {
    private UserList userList = UserList.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(userList.toString());

        if (json != null){
            PrintWriter pw = response.getWriter();
            pw.print(json);
        }
    }
}