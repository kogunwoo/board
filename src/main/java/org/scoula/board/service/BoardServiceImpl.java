package org.scoula.board.service;

import lombok.RequiredArgsConstructor;
import org.scoula.board.dto.BoardDTO;
import org.springframework.stereotype.Service;
import org.scoula.board.domain.BoardVO;
import org.scoula.board.mapper.BoardMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j
@Service  //bean에 등록하라는 뜻
@RequiredArgsConstructor //생성자 주입하기 위해서
   public class BoardServiceImpl implements BoardService {

    final private BoardMapper mapper;

    @Override
    public List<BoardDTO> getList() {
        log.info("getList..........");
        return mapper.getList().stream() // BoardVO의 스트림
                .map(BoardDTO::of) // BoardDTO의 스트림
                .toList(); // List<BoardDTO> 변환
    }
    @Override
    public BoardDTO get(Long no) {
        log.info("get......" + no);

        BoardDTO board = BoardDTO.of(mapper.get(no));
        return Optional.ofNullable(board)
                .orElseThrow(NoSuchElementException::new);
    }
//    @Override
//    public void create(BoardDTO board) {
//        log.info("create......" + board);
//        mapper.create(board.toVo());
//    }
    @Override
    public void create(BoardDTO board) {
        BoardVO vo = board.toVo();
        mapper.create(vo); // This should insert and set the ID in the database
        board.setNo(vo.getNo()); // Ensure the DTO gets updated with the generated ID

    }
    @Override
    public boolean update(BoardDTO board) {
        log.info("update......" + board);
        return mapper.update(board.toVo()) == 1;
    }
    @Override
    public boolean delete(Long no) {
        log.info("delete...." + no);
        return mapper.delete(no) == 1; }
}


