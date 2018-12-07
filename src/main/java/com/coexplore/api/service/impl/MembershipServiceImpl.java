package com.coexplore.api.service.impl;

import com.coexplore.api.service.MembershipService;
import com.coexplore.api.domain.Membership;
import com.coexplore.api.repository.MembershipRepository;
import com.coexplore.api.service.dto.MembershipDTO;
import com.coexplore.api.service.mapper.MembershipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Membership.
 */
@Service
@Transactional
public class MembershipServiceImpl implements MembershipService {

    private final Logger log = LoggerFactory.getLogger(MembershipServiceImpl.class);

    private final MembershipRepository membershipRepository;

    private final MembershipMapper membershipMapper;

    public MembershipServiceImpl(MembershipRepository membershipRepository, MembershipMapper membershipMapper) {
        this.membershipRepository = membershipRepository;
        this.membershipMapper = membershipMapper;
    }

    /**
     * Save a membership.
     *
     * @param membershipDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MembershipDTO save(MembershipDTO membershipDTO) {
        log.debug("Request to save Membership : {}", membershipDTO);

        Membership membership = membershipMapper.toEntity(membershipDTO);
        membership = membershipRepository.save(membership);
        return membershipMapper.toDto(membership);
    }

    /**
     * Get all the memberships.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MembershipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Memberships");
        return membershipRepository.findAll(pageable)
            .map(membershipMapper::toDto);
    }


    /**
     * Get one membership by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MembershipDTO> findOne(Long id) {
        log.debug("Request to get Membership : {}", id);
        return membershipRepository.findById(id)
            .map(membershipMapper::toDto);
    }

    /**
     * Delete the membership by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Membership : {}", id);
        membershipRepository.deleteById(id);
    }
}
