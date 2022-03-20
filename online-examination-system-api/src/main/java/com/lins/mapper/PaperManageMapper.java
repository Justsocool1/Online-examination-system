package com.lins.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lins.vo.PaperVO;

import java.util.List;

public interface PaperManageMapper {
    int addPaper(Integer paperId, Integer questionId, Integer questionType);

    int deletePaperByPaperId(Integer paperId);

    List<PaperVO> getPapersByCondition(Page<PaperVO> paperPage, String subject, String section, String questionType, String level);
}
