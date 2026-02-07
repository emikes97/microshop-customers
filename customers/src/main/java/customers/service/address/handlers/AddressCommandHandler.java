package customers.service.address.handlers;

import customers.service.address.commands.CreateNewAddress;
import customers.service.address.commands.DeleteAddress;
import customers.service.address.commands.UpdateAddress;
import customers.service.address.commands.UpdateDefault;
import customers.web.DTO.Requests.CustomerAddress.DTOCustomerAddressNewAddress;
import customers.web.DTO.Requests.CustomerAddress.DTOCustomerAddressUpdateAddress;
import customers.web.DTO.Responses.Address.DTONewAddressResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AddressCommandHandler {

    // == Fields ==
    private final CreateNewAddress create;
    private final UpdateAddress update;
    private final UpdateDefault updateDefault;
    private final DeleteAddress delete;

    // == Constructors ==
    public AddressCommandHandler(CreateNewAddress create, UpdateAddress update, UpdateDefault updateDefault, DeleteAddress delete) {
        this.create = create;
        this.update = update;
        this.updateDefault = updateDefault;
        this.delete = delete;
    }

    // == Public Methods ==

    @Transactional
    public DTONewAddressResponse createNewAddress(UUID customerId, DTOCustomerAddressNewAddress dto){
        return create.handle(customerId, dto);
    }

    @Transactional
    public void updateExistingAddress(UUID customerId, long addressId, DTOCustomerAddressUpdateAddress dto){
        update.handle(customerId, addressId, dto);
    }

    @Transactional
    public void removeDefaultAddress(UUID customerId){
        updateDefault.removeDefault(customerId);
    }

    @Transactional
    public void setNewDefaultAddress(UUID customerId, long addressId){
        updateDefault.setNewDefault(customerId, addressId);
    }

    @Transactional
    public void deleteAddress(UUID customerId, long addressId){
        delete.handle(customerId, addressId);
    }
}
