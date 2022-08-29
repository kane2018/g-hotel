package com.kane.hotel.service;

import com.kane.hotel.dao.CommentRepository;
import com.kane.hotel.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment ajouter(Comment comment) {
        return commentRepository.save(comment);
    }
}
