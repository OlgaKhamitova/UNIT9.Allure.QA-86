package utils;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class RegistrationByCardInfo {
    String city;
    String name;
    String lastName;
    String phoneNumber;
}
