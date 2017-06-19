import java.util.ArrayList;

public class Kpp {

	public static void main(String[] args) {

		ArrayList<Car> cars = new ArrayList<>();
		cars.add(new Car(3, 2, 2, 75, false));
		cars.add(new Car(3, 2, 2, 200, true));
		cars.add(new FreightCar(6, 2, 5, 85, false, 10, 3, false));
		cars.add(new FreightCar(6, 2, 5, 85, false, 10, 3, true));
		cars.add(new FreightCar(7, 2, 15, 70, false, 15, 3, false));
		cars.add(new FreightCar(7, 2, 15, 70, false, 15, 5, false));
		cars.add(new FreightCar(10, 2, 15, 70, true, 15, 3, false));

		CheckPoint cp = new CheckPoint();

		cp.setAllowedSpeed(80);
		cp.setMaxCarrying(10);
		cp.setMaxHeight(4);
		cp.setMaxLength(15);
		cp.setMaxWidth(3);
		cp.setMaxSpeed(180);
		cp.setMaxWeight(10);

		for (Car c : cars) {
			try {
				c.check(cp);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println();
			}
		}

	}

	public static class Car { // Легковой автомобиль
		protected float length; // длина
		protected float width; // ширина
		protected float weight; // вес
		protected float speed; // скорость
		protected boolean special; // является ли спецтранспортом

		public Car(float length, float width, float weight, float speed, boolean special) {
			super();
			this.length = length;
			this.width = width;
			this.weight = weight;
			this.speed = speed;
			this.special = special;
		}

		public float getLength() {
			return length;
		}

		public float getWidth() {
			return width;
		}

		public float getSpeed() {
			return speed;
		}

		public float getWeight() {
			return weight;
		}

		public boolean isSpecial() {
			return special;
		}

		public void setLength(float length) {
			this.length = length;
		}

		public void setWidth(float width) {
			this.width = width;
		}

		public void setSpeed(float speed) {
			this.speed = speed;
		}

		public void setWeight(float weight) {
			this.weight = weight;
		}

		public void setSpecial(boolean special) {
			this.special = special;
		}

		public void check(CheckPoint cp) throws Exception {
			System.out.println(this);
			float cost = cp.checkCost(this);
			System.out.printf("Стоимость прозда КПП: %.2f р.\n\n", cost);
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return String.format(
					"Легковой автомобиль [Длина: %.1f м, ширина: %.1f м, масса: %.1f т, скорость: %.1f км/ч, спец. транспорт: %s]",
					length, width, weight, speed, special ? "да" : "нет");
		}

	}

	public static class FreightCar extends Car { // грузовой автомобиль
		protected float carrying; // грузоподъемность
		protected float height; // высота
		protected boolean trailer; // есть ли прицеп

		public FreightCar(float length, float width, float weight, float speed, boolean special, float carrying,
				float height, boolean trailer) {
			super(length, width, weight, speed, special);
			this.carrying = carrying;
			this.height = height;
			this.trailer = trailer;
		}

		public float getCarrying() {
			return carrying;
		}

		public float getHeight() {
			return height;
		}

		public boolean hasTrailer() {
			return trailer;
		}

		public void setCarrying(float carrying) {
			this.carrying = carrying;
		}

		public void setHeight(float height) {
			this.height = height;
		}

		public void setTrailer(boolean trailer) {
			this.trailer = trailer;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return String.format(
					"Грузовой автомобиль [Длина: %.1f м, ширина: %.1f м, высота: %.1f м, масса: %.1f т, грузоподъемность: %.1f т, скорость: %.1f км/ч, спец. транспорт: %s]",
					length, width, height, weight, carrying, speed, special ? "да" : "нет");
		}

	}

	public static class CheckPoint { // КПП

		protected float maxLength;
		protected float maxWidth;
		protected float maxHeight;
		protected float maxWeight;
		protected float maxCarrying;
		protected float allowedSpeed;
		protected float maxSpeed;

		public float checkCost(Car car) throws Exception {


			float cost = 0;

			if (car.isSpecial()) {
				return cost;
			}

			checkDimensions(car);
			
			if (car instanceof FreightCar) {
				cost += ((FreightCar) car).hasTrailer() ? 2500 : 2000;
			} else {
				cost += 1000;
			}

			if (car.getWeight() > maxWeight) {
				cost += 800;
			}

			if (car.getSpeed() > allowedSpeed) {

				if (car.getSpeed() > maxSpeed) {
					throw new Exception("Превышена максимальная скорость 180 км/ч. ГИБДД");
				}

				if (car instanceof FreightCar) {
					cost += 1000; // для грузовиков за превышение платим 1000
				} else {
					cost += 500; // для легковых платим 500
				}
			}

			return cost;
		}

		protected void checkDimensions(Car car) throws Exception { // проверяем
																	// габариты

			if (car instanceof FreightCar && ((FreightCar) car).getHeight() > maxHeight) {
				throw new Exception("Габариты грузового автомодиля больше допустимых.");
			}

			if (car instanceof FreightCar && ((FreightCar) car).getCarrying() > maxCarrying) {
				throw new Exception("Грузоподъемность грузового автомодиля больше допустимых.");
			}
		}

		public float getMaxLength() {
			return maxLength;
		}

		public float getMaxWidth() {
			return maxWidth;
		}

		public float getMaxHeight() {
			return maxHeight;
		}

		public void setMaxLength(float maxLength) {
			this.maxLength = maxLength;
		}

		public void setMaxWidth(float maxWidth) {
			this.maxWidth = maxWidth;
		}

		public void setMaxHeight(float maxHeight) {
			this.maxHeight = maxHeight;
		}

		public float getMaxWeight() {
			return maxWeight;
		}

		public void setMaxWeight(float maxWeight) {
			this.maxWeight = maxWeight;
		}

		public float getMaxCarrying() {
			return maxCarrying;
		}

		public float getAllowedSpeed() {
			return allowedSpeed;
		}

		public float getMaxSpeed() {
			return maxSpeed;
		}

		public void setMaxCarrying(float maxCarrying) {
			this.maxCarrying = maxCarrying;
		}

		public void setAllowedSpeed(float allowedSpeed) {
			this.allowedSpeed = allowedSpeed;
		}

		public void setMaxSpeed(float maxSpeed) {
			this.maxSpeed = maxSpeed;
		}

	}
}

