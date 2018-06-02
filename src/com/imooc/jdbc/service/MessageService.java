package com.imooc.jdbc.service;

import com.imooc.jdbc.bean.Message;
import com.imooc.jdbc.dao.MessageDAO;

import java.util.Date;
import java.util.List;

/**
 * 消息Service
 *
 * @version 1.0
 */
public class MessageService {

    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public boolean addMessage(Message message) {
        message.setCreateTime(new Date());
        return messageDAO.save(message);
    }

    /**
     * 分页查询全部留言
     * @param page 当前页码
     * @param pageSize 每页记录数
     * @return
     */
    public List<Message> getMessages(int page, int pageSize) {
        return messageDAO.getMessages(page, pageSize);
    }

    public List<Message> getMyMessages(int page, int pageSize,String username){
        return messageDAO.getMyMessages(page,pageSize,username);
    }

    /**
     * 计算所有留言数量
     * @return
     */
    public int countMessages() {
        return messageDAO.countMessages();
    }
    public void updateMsg(long id, long userid,String title,String content) {
        messageDAO.updateMsg(id,userid,title,content);
    }

    public void deleteMsg(long id, long userid, String username) {
        messageDAO.deleteMsg(id,userid,username);
    }
}
