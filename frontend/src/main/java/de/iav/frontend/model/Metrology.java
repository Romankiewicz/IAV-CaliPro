package de.iav.frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Metrology(
        String metrologyId,
        String iavInventory,
        String manufacturer,
        String type,
        Date maintenance,
        Date calibration
) {

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Metrology other = (Metrology) object;

        return Objects.equals(this.metrologyId, other.metrologyId)
                && Objects.equals(this.iavInventory, other.iavInventory)
                && Objects.equals(this.manufacturer, other.manufacturer)
                && Objects.equals(this.type, other.type)
                && Objects.equals(this.maintenance, other.maintenance)
                && Objects.equals(this.calibration, other.calibration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metrologyId,
                iavInventory,
                manufacturer,
                type,
                maintenance,
                calibration);
    }
}
