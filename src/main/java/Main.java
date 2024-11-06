import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.bancohipotecario.dto.MotorBikePriceDTO;
import com.bancohipotecario.interfaces.IMotorBikeController;

import java.util.ArrayList;
import java.util.List;

public class Main {

	private static ApplicationContext context;

	public static void main(String[] args) {
		context = new AnnotationConfigApplicationContext(AppConfig.class);

		IMotorBikeController motoController = context.getBean(IMotorBikeController.class);
		List<MotorBikePriceDTO> motorBikePriceList = new ArrayList<>();
		motorBikePriceList = motoController.getAveragePricesByBrand();
		motorBikePriceList.forEach(
				dto -> System.out.println("Marca: " + dto.getBrand() + ", Precio promedio: " + dto.getAveragePrice()));

	}
}