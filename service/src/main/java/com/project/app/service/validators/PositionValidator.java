package com.project.app.service.validators;

import com.project.app.service.exceptions.InvalidPositionDuringSaveException;
import com.project.app.service.exceptions.calcvalidation.InvalidPositionStateBeforeCalculationException;
import com.project.app.entities.position.Position;

import java.util.Objects;

public class PositionValidator extends CrudValidator<Position> implements CalculationValidator<Position> {

    public PositionValidator(Position position) {
        super(position);
    }

    @Override
    public void onSave(Position entity) {
        getValidator().setObject(entity);
        getValidator().validate((pos) -> {
            return Objects.isNull(pos.getId())
                    && Objects.equals(pos.getPositionVolume(), 0.0)
                    && Objects.equals(pos.getPrincipal(), 0.0)
                    && Objects.nonNull(pos.getInstrument());
        }, new InvalidPositionDuringSaveException());
    }

    @Override
    protected Long getEntityId(Position entity) {
        return entity.getId();
    }

    @Override
    public void onCalculation(Position entity) {
        getValidator().setObject(entity);
        getValidator().validate(this::onCalc,
                new InvalidPositionStateBeforeCalculationException());
    }

    private boolean onCalc(Position pos) {
        final boolean notNullId = Objects.nonNull(pos.getId());
        final boolean doDatesMatch = pos.getStartingDateOfDeal().isEqual(pos.getInstrument().getStartOfPaymentPeriod());
        final boolean isVolumeValid = pos.getPositionVolume() > 0;
        final boolean isPrincipalValid = pos.getPrincipal() > 0;
        return notNullId && doDatesMatch && isVolumeValid && isPrincipalValid;
    }

}
