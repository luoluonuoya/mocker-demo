package demo.facade.model.rsp;

import lombok.Data;

@Data
public class BaseUser {

    private String token;

    private Long id;

    private String guid;

    private String account;

    private String name;

    private String mobile;

    private Long accessId;

}
