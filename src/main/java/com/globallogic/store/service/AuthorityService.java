package com.globallogic.store.service;

import com.globallogic.store.dao.GenericDao;
import com.globallogic.store.dao.criteria.SearchCriteria;
import com.globallogic.store.domain.user.Authority;
import com.globallogic.store.domain.user.AuthorityName;
import com.globallogic.store.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {

    private GenericDao<Authority> authorityDao;

    public AuthorityService(GenericDao<Authority> authorityDao) {
        this.authorityDao = authorityDao;
    }

    public Authority getById(long id) throws NotFoundException {
        Authority authority = authorityDao.getEntityByKey(id);

        if (authority == null) {
            throw new NotFoundException("Authority with id=" + id + " not found!");
        } else {
            return authority;
        }
    }

    public Authority getByTitle(String title) throws NotFoundException {
        SearchCriteria criteria = new SearchCriteria().criteria("title", AuthorityName.valueOf(title));
        Authority authority = authorityDao.getEntity(criteria);

        if (authority == null) {
            throw new NotFoundException("Authority " + title + " not found!");
        } else {
            return authority;
        }
    }
}
