package org.example.tltravel.service.impl;

import org.example.tltravel.compositekeys.UserRoleId;
import org.example.tltravel.entities.*;
import org.example.tltravel.exceptions.TLEntityNotActive;
import org.example.tltravel.exceptions.TLEntityNotFound;
import org.example.tltravel.repositories.*;
import org.example.tltravel.service.IUserService;
import org.example.tltravel.view.in.AgentFields;
import org.example.tltravel.view.in.ClientFields;
import org.example.tltravel.view.in.OperatorFields;
import org.example.tltravel.view.in.UserInView;
import org.example.tltravel.view.out.UserOutView;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class UserServiceImpl implements IUserService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    public UserServiceImpl(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    public Authentication login(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserOutView createUser(UserInView inView) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : addUser start", logId);

        // 2. Fetch Role
        RoleEntity role = roleRepository.findByNameAndActiveTrue(inView.getRole().toUpperCase())
                .orElseThrow(() -> new TLEntityNotFound("Role not found"));

        // 1. Save base user
        UserEntity user = new UserEntity();
        user.setEmail(inView.getEmail());
        user.setPassword(passwordEncoder.encode(inView.getPassword()));
        user.setActive(true);
        user = userRepository.save(user);



        // 3. Link user to role
        UserRoleId userRoleId = new UserRoleId(user.getId(), role.getId());
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setUser_id(user.getId());
        userRole.setRole_id(role.getId());
        userRole.setActive(true);
        userRoleRepository.save(userRole);

        UserOutView outView = new UserOutView();
        outView.setEmail(user.getEmail());
        outView.setRole(role.getName());

        // 4. Create role-specific data
        switch (role.getName().toUpperCase()) {
            case "ROLE_OPERATOR" -> {
                OperatorEntity operator = new OperatorEntity();
                OperatorFields operatorFields = inView.getOperatorFields();
                operator.setName(operatorFields.getName());
                operator.setPhone(operatorFields.getPhone());
                operator.setAddress(operatorFields.getAddress());
                operator.setActive(true);
                operator.setUser(user);
                operatorRepository.save(operator);
//                operatorFields = modelMapper.map(operator,OperatorFields.class);
                outView.setOperator(operatorFields);
            }
            case "ROLE_AGENT" -> {
                AgentEntity agent = new AgentEntity();
//                agent.setUser(user);
                AgentFields agentFields = inView.getAgentFields();
                agent.setName(agentFields.getName());
                agent.setCommissionPercent(BigDecimal.valueOf(agentFields.getCommissionPercent()));
                agent.setActive(true);
                agent.setUser(user);
                agentRepository.save(agent);

//                AgentFields agentFields = modelMapper.map(agent,AgentFields.class);
                outView.setAgent(agentFields);
            }
            case "ROLE_CLIENT" -> {
                ClientEntity client = new ClientEntity();
//                client.setUser(user);
                ClientFields clientFields = inView.getClientFields();
                client.setName(clientFields.getName());
                client.setPhone(clientFields.getPhone());
                client.setActive(true);
                client.setUser(user);
                clientRepository.save(client);

//                ClientFields clientFields = modelMapper.map(client, ClientFields.class);
                outView.setClient(clientFields);
            }
            default -> throw new IllegalArgumentException("Unsupported role: " + inView.getRole());
        }
        outView.setId(user.getId());

        log.info("{} : addUser finished", logId);
        return outView;
    }

    @Override
    public Page<UserOutView> getAll(Pageable pageable) throws TLEntityNotFound {
        String logId = UUID.randomUUID().toString();
        log.info("{} : getAll start", logId);
        try{
            Page<UserEntity> entities = userRepository.findAllActive(pageable);
//            for (UserEntity entity:entities) {
//                UserRoleEntity userRole = userRoleRepository.findByUserId(entity.getId());
//                Optional<RoleEntity> roleEntity = roleRepository.findByIdAndActiveTrue(userRole.getRole_id());
//                String role = roleEntity.get().getName();
//            }





            List<UserOutView> userOutViews = modelMapper.map(entities.getContent(),
                    new TypeToken<List<UserOutView>>(){}.getType());

            Page<UserOutView> res = new PageImpl<>(userOutViews,pageable,entities.getTotalElements());


            return res;

        }catch (Exception e){
            log.error("{}: getAll error", logId,e);
            throw e;
        }finally {
            log.info("{}: getAll finished", logId);
        }
    }

    @Override
    public Optional<UserOutView> getById(Long id) throws TLEntityNotFound {
        return Optional.empty();
    }

    @Override
    public UserOutView update(Long id, UserInView inView) throws TLEntityNotFound {
        return null;
    }

//    @Override
//    public void delete(Long id) throws TLEntityNotFound, TLEntityNotActive {
//
//    }

//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private UserRepository userRepo;
//    @Autowired private RoleRepository roleRepo;
//    @Autowired private AgentRepository agentRepo;
//    @Autowired private ClientRepository clientRepo;
//    @Autowired private OperatorRepository operatorRepo;
//    @Autowired private ModelMapper modelMapper;
//    @Autowired private PasswordEncoder passwordEncoder;
//    @Override
//    @Transactional
//    public UserOutView createUser(UserInView inView) {
//        String logId = UUID.randomUUID().toString();
//        log.info("{} : createUser start", logId);
//        log.debug("{} : payload: {}", logId, inView);
//
//        if (inView.getEmail() == null || !inView.getEmail().contains("@")) {
//            throw new IllegalArgumentException("Valid email is required.");
//        }
//        if (inView.getPassword() == null || inView.getPassword().length() < 6) {
//            throw new IllegalArgumentException("Password must be at least 6 characters.");
//        }
//        if (inView.getRoles() == null || inView.getRoles().isEmpty()) {
//            throw new IllegalArgumentException("At least one role must be provided.");
//        }
//
//        if (inView.getRoles().contains("ROLE_ADMIN")) {
//            long adminCount = userRepo.countByRoles_Name("ROLE_ADMIN");
//            if (adminCount >= 1) {
//                throw new IllegalStateException("Cannot create a second ADMIN user.");
//            }
//        }
//
//        validateCreateUser(inView);
//
//        // 1) map & persist base user
//        UserEntity user = new UserEntity();
//        user.setEmail(inView .getEmail());
//        user.setPassword(passwordEncoder.encode(inView.getPassword()));
//        user.setEnabled(true);
//        user.setActive(true);
//
//        // fetch RoleEntity objects
////        Set<RoleEntity> roles = inView.getRoles().stream()
////                .map(rn -> {
////                    try {
////                        return roleRepo.findByName(rn)
////                                .orElseThrow(() -> new TLEntityNotFound("Role "+rn+" not found"));
////                    } catch (TLEntityNotFound e) {
////                        throw new RuntimeException("Role not found: " + rn, e);
////                    }
////                })
////                .collect(Collectors.toSet());
////        user.setRoles(roles);
//        List<RoleEntity> foundRoles = roleRepo.findAllByNameIn(inView.getRoles());
//        if (foundRoles.size() != inView.getRoles().size()) {
//            throw new IllegalArgumentException("Invalid role name in: " + inView.getRoles());
//        }
//        user.setRoles(new HashSet<>(foundRoles));
//
//
////        user = userRepo.save(user);
//
//        // 2) for each role, create the corresponding child entity
//        if (inView.getRoles().contains("ROLE_AGENT")) {
//            AgentEntity agent = new AgentEntity();
//            AgentFields af = inView.getAgent();
//            agent.setName(af.getName());
//            agent.setCommissionPercent(BigDecimal.valueOf(af.getCommissionPercent()));
//            agent.setUser(user);
//            agent.setActive(true);
////            agentRepo.save(agent);
//            user.setAgent(agent);
//        }
//
//
//        if (inView.getRoles().contains("ROLE_CLIENT")) {
//            ClientEntity client = new ClientEntity();
//            ClientFields cf = inView.getClient();
//            client.setName(cf.getName());
//            client.setPhone(cf.getPhone());
//            client.setUser(user);
//            client.setActive(true);
////            clientRepo.save(client);
//            user.setClient(client);
//        }
//        if (inView.getRoles().contains("ROLE_OPERATOR")) {
//            OperatorEntity op = new OperatorEntity();
//            OperatorFields of = inView.getOperator();
//            op.setName(of.getName());
//            op.setPhone(of.getPhone());
//            op.setAddress(of.getAddress());
//            op.setUser(user);
//            op.setActive(true);
////            operatorRepo.save(op);
//            user.setOperator(op);
//        }
//
//        // 3) return view
//        UserEntity saved = userRepo.save(user);
//        UserOutView out = modelMapper.map(saved, UserOutView.class);
//        log.info("{} : createUser finished", logId);
//        return out;
//
//    }
//
//    @Override
//    public Page<UserOutView> getAll(Pageable pageable) throws TLEntityNotFound {
//        String logId = UUID.randomUUID().toString();
//        log.info("{} : getAllUsers start", logId);
//        try {
//            Page<UserEntity> users = userRepo.findAll(pageable);
//            List<UserOutView> views = modelMapper.map(
//                    users.getContent(),
//                    new TypeToken<List<UserOutView>>() {}.getType()
//            );
//            return new PageImpl<>(views, pageable, users.getTotalElements());
//        } catch (Exception e) {
//            log.error("{} : getAllUsers error", logId, e);
//            throw e;
//        } finally {
//            log.info("{} : getAllUsers finished", logId);
//        }
//    }
//
//    @Override
//    public Optional<UserOutView> getById(Long id) throws TLEntityNotFound {
//        String logId = UUID.randomUUID().toString();
//        log.info("{} : getUserById start", logId);
//        try {
//            Optional<UserEntity> userOpt = userRepo.findById(id);
//            if (userOpt.isEmpty()) {
//                return Optional.empty();
//            }
//            Optional<UserOutView> view = modelMapper.map(
//                    userOpt, new TypeToken<Optional<UserOutView>>() {}.getType()
//            );
//            return view;
//        } catch (Exception e) {
//            log.error("{} : getUserById error", logId, e);
//            throw e;
//        } finally {
//            log.info("{} : getUserById finished", logId);
//        }
//    }
//
//    @Override
//    public UserOutView update(Long id, UserInView inView) throws TLEntityNotFound {
//        String logId = UUID.randomUUID().toString();
//        log.info("{} : updateUser start", logId);
//        log.debug("{} : params: id: {}, body: {}", logId, id, inView);
//        try {
//            UserEntity existing = userRepo.findById(id)
//                    .orElseThrow(TLEntityNotFound::new);
//
//            // update fields
//            existing.setEmail(inView.getEmail());
//            if (inView.getPassword() != null && !inView.getPassword().isBlank()) {
//                existing.setPassword(passwordEncoder.encode(inView.getPassword()));
//            }
//            // roles
//            Set<RoleEntity> roles = inView.getRoles().stream()
//                    .map(rn -> {
//                        try {
//                            return roleRepo.findByName(rn).orElseThrow(() -> new TLEntityNotFound("Role " + rn + " not found"));
//                        } catch (TLEntityNotFound e) {
//                            throw new RuntimeException(e);
//                        }
//                    })
//                    .collect(Collectors.toSet());
//            existing.setRoles(roles);
//
//            UserEntity saved = userRepo.save(existing);
//            return modelMapper.map(saved, UserOutView.class);
//        } catch (Exception e) {
//            log.error("{} : updateUser error", logId, e);
//            throw e;
//        } finally {
//            log.info("{} : updateUser finished", logId);
//        }
//    }
//
    @Override
    public void delete(Long id) throws TLEntityNotFound, TLEntityNotActive {

        String logId = UUID.randomUUID().toString();
        log.info("{} : deleteUser start", logId);
        try {
            UserEntity user = userRepository.findById(id).orElseThrow(TLEntityNotFound::new);
            if (!user.isEnabled()) {
                throw new TLEntityNotActive("User " + id + " already disabled");
            }
            user.setEnabled(false);
            userRepository.save(user);
        } catch (Exception e) {
            log.error("{} : deleteUser error", logId, e);
            throw e;
        } finally {
            log.info("{} : deleteUser finished", logId);
        }
    }
//
//    private void validateCreateUser(UserInView inView) {
//        // Validate email/password not null, well‐formed, etc.
//        if (inView.getEmail() == null || !inView.getEmail().contains("@")) {
//            throw new IllegalArgumentException("Email is required and must be valid.");
//        }
//        if (inView.getPassword() == null || inView.getPassword().length() < 6) {
//            throw new IllegalArgumentException("Password is required and must be at least 6 chars.");
//        }
//        Set<String> roles = inView.getRoles();
//        if (roles == null || roles.isEmpty()) {
//            throw new IllegalArgumentException("At least one role must be specified.");
//        }
//        // If ROLE_CLIENT is present, ensure inView.getClient() is nonnull and has name/phone:
//        if (roles.contains("ROLE_CLIENT")) {
//            if (inView.getClient() == null ||
//                    inView.getClient().getName() == null ||
//                    inView.getClient().getPhone() == null) {
//                throw new IllegalArgumentException("Client details (name/phone) are required.");
//            }
//        }
//        // If ROLE_OPERATOR is present, ensure inView.getOperator() is nonnull and has name/phone/address
//        if (roles.contains("ROLE_OPERATOR")) {
//            if (inView.getOperator() == null ||
//                    inView.getOperator().getName() == null ||
//                    inView.getOperator().getPhone() == null ||
//                    inView.getOperator().getAddress() == null) {
//                throw new IllegalArgumentException("Operator details (name/phone/address) are required.");
//            }
//        }
//
//        // Similarly for ROLE_AGENT…
//        if (roles.contains("ROLE_AGENT")) {
//            if (inView.getAgent() == null ||
//                    inView.getAgent().getName() == null ||
//                    inView.getAgent().getCommissionPercent() == null ){
//                throw new IllegalArgumentException("Agent details (name/commissionPercent) are required.");
//            }
//        }
//    }
}

