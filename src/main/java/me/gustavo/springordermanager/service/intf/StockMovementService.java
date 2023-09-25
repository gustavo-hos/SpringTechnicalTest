package me.gustavo.springordermanager.service.intf;

import me.gustavo.springordermanager.model.StockMovement;
import me.gustavo.springordermanager.model.dto.StockMovementDto;

import java.util.Optional;

public interface StockMovementService extends CRUDService<StockMovement, Long> {

    Optional<StockMovement> create(StockMovementDto stockMovementDto);

    Optional<StockMovement> update(StockMovementDto stockMovementDto);

}
