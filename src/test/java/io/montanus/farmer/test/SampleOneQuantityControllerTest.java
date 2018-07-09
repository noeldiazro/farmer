package io.montanus.farmer.test;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class SampleOneQuantityControllerTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void succesfullSample() {
        final Quantity irrelevantQuantity = Quantity.from(35);
        final Sensor sensor = context.mock(Sensor.class);
        final Display display = context.mock(Display.class);

        context.checking(new Expectations() {{
            allowing(sensor).sample();
            will(returnValue(irrelevantQuantity));

            oneOf(display).displayQuantity(with(irrelevantQuantity));
        }});

        AcquisitionController acquisitionController = new AcquisitionController(sensor, display);
        acquisitionController.onSample();
    }

    private static class Quantity {
        public static Quantity from(int intValue) {
            return new Quantity();
        }
    }

    private interface Sensor {
        Quantity sample();
    }

    private interface Display {
        void displayQuantity(Quantity quantity);
    }

    private static class AcquisitionController {
        private final Sensor sensor;
        private final Display display;

        public AcquisitionController(Sensor sensor, Display display) {
            this.sensor = sensor;
            this.display = display;
        }

        public void onSample() {
            display.displayQuantity(sensor.sample());
        }
    }
}
