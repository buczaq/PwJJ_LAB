import java.sql.*;
import org.mariadb.jdbc.*;

public class GetData {

    // ABGGBA
    public static void main(String [ ] args)
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/bacteria?user=buczaq&password=123456");
            System.out.println("connected");

            Statement statement = connection.createStatement();
            ResultSet result_set1 = statement.executeQuery("SELECT * FROM flagella");
            ResultSet result_set2 = statement.executeQuery("SELECT * FROM toughness");

            /*
            while (result_set.next()) {
                int a = result_set.getInt("alpha");
                int b = result_set.getInt("beta");
                int n = result_set.getInt("number");
                System.out.println(a + "\t" + b + "\t" + n);
            }
            */

            int gen = 731219;

            int TA = 0;
            int TB = 0;
            int TC = 0;
            int TD = 0;
            int F1 = 0;
            int F2 = 0;
            int F3 = 0;
            int F4 = 0;

            int temp[] = new int[6];

            for (int i = 5; i >= 0; i--) {
                temp[i] = gen%10;
                gen /= 10;
            }

            int alpha = temp[0] * 10 + temp[5];
            int beta = temp[1] * 10 + temp[4];
            int gamma = temp[2] * 10 + temp[3];

            System.out.println("examined gene is: ALPHA=" + alpha + " BETA=" + beta + " GAMMA=" + gamma);

            while (result_set1.next()) {
                int a = result_set1.getInt("alpha");
                int b = result_set1.getInt("beta");
                int n = result_set1.getInt("number");
                if (Math.abs(a - alpha) <= 20 && Math.abs(b - beta) <= 20) {
                    if (n == 1) F1++;
                    else if (n == 2) F2++;
                    else if (n == 3) F3++;
                    else if (n == 4) F4++;
                }
            }

            while (result_set2.next()) {
                int b = result_set2.getInt("beta");
                int g = result_set2.getInt("gamma");
                String t = result_set2.getString("rank");
                if (Math.abs(b - beta) <= 20 && Math.abs(g - gamma) <= 20) {
                    if (t.equals("a")) TA++;
                    else if (t.equals("b")) TB++;
                    else if (t.equals("c")) TC++;
                    else if (t.equals("d")) TD++;
                }
            }

            System.out.println("TA:" + TA +" TB:" + TB +" TC:" + TC +" TD:" + TD +" F1:" + F1 +" F2:" + F2 + " F3:" + F3 + " F4:" + F4);
        }
        catch (Exception e) {
            System.out.println("error :( " + e);
        }
    }

}
