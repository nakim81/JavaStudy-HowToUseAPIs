package kakao.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KakaoMapMain {
    public static void main(String[] args) {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter address: ");
            String address = reader.readLine();

            double[] coordinates = KakaoAPI.getAddressCoordinate(address);

            if (coordinates != null) {
                System.out.println("address: " + address);
                System.out.println("latitude: " + coordinates[0]);
                System.out.println("longitude: " + coordinates[1]);
            } else {
                System.out.println("Unable to find the address");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
