package edu.umass.flashsale.database;

import edu.umass.flashsale.model.OrderfulfillmentData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderfulfillmentRepository  extends JpaRepository<OrderfulfillmentData, Long> {

}
