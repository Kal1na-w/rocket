package ua.od.atomspace.sarafan.resources.resourceAssemvler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import ua.od.atomspace.sarafan.domain.User;
import ua.od.atomspace.sarafan.resources.UserResources;

public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResources> {

    public UserResourceAssembler() {
        super(User.class,UserResources.class);
    }

    @Override
    protected UserResources instantiateResource(User user) {
        return new UserResources(user);
    }


    @Override
    public UserResources toResource(User user) {
        return createResourceWithId(user.getId(),user);
    }
}
