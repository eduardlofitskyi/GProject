package com.lofitskyi.service.springdata;

import com.lofitskyi.entity.User;
import com.lofitskyi.repository.springdata.UserRepository;
import com.lofitskyi.repository.PersistentException;
import com.lofitskyi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    public UserServiceImpl() {
    }

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public User create(User user) throws SQLException, PersistentException {
        return repository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void update(User user) throws PersistentException {
        repository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void remove(User user) throws PersistentException {
        repository.delete(user);
    }


    @Override
    public List<User> findAll() throws PersistentException {
        return repository.findAll();
    }

    @Override
    public Page<User> findAllPagable(int pageNumber) throws PersistentException {
        PageRequest request = new PageRequest(pageNumber, 3, Sort.Direction.ASC, "login");
        return repository.findAll(request);
    }

    @Override
    public User findByLogin(String login) throws PersistentException {
        User user = repository.findByLogin(login);

        if (user == null) throw new PersistentException("No such user");

        return user;
    }

    @Override
    public User findByEmail(String email) throws PersistentException {
        User user = repository.findByEmail(email);

        if (user == null) throw new PersistentException("No such user");

        return user;
    }

    @Override
    public User findById(Long id) throws PersistentException {
        User user = repository.findById(id);

        if (user == null) throw new PersistentException("No such user");

        return user;
    }
}
