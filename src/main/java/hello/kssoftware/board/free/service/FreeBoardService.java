package hello.kssoftware.board.free.service;

import hello.kssoftware.board.common.BoardDto;
import hello.kssoftware.board.common.BoardService;
import hello.kssoftware.board.free.dto.FreeBoardDto;
import hello.kssoftware.board.free.entity.FreeBoard;
import hello.kssoftware.board.common.BoardRepository;
import org.springframework.stereotype.Service;

@Service
public class FreeBoardService extends BoardService<FreeBoard, FreeBoardDto> {
    public FreeBoardService(BoardRepository<FreeBoard> boardRepository) {
        super(boardRepository);
    }

    public Long createBoard(BoardDto.Create createDto) {
        FreeBoardDto.Create dto = (FreeBoardDto.Create) createDto;
        FreeBoard board = FreeBoard.builder()
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .content(dto.getContent())
                .createDate(now())
                .updateDate(now())
                .build();

        return boardRepository.save(board);
    }

    public void updateBoard(BoardDto.Update updateDto) {
        FreeBoardDto.Update dto = (FreeBoardDto.Update) updateDto;
        FreeBoard board = boardRepository.findById(dto.getId());
        board.update(dto.getTitle(), dto.getContent(), now());
    }
}
