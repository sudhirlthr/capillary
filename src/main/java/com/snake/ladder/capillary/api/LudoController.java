package com.snake.ladder.capillary.api;

import com.snake.ladder.capillary.service.impl.LudoServiceImpl;
import com.snake.ladder.capillary.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LudoController {

    @Autowired
    private LudoServiceImpl ludoService;

    @GetMapping("/start")
    public String startGameViaGetRequest(){
        return ludoService.initializeLudo(Constants.START);
    }
    @PostMapping("/start")
    public String startGame(@RequestBody Map<String, String> body){
        if (body.containsKey(Constants.START)){
            return ludoService.initializeLudo(Constants.START);
        }
        else return Constants.NOT_READY;
    }

    @GetMapping("/dice/{dice}")
    public String play(@PathVariable Integer dice){
        if (ludoService.getLudoEntity() != null){
            if (dice < 0 || dice > 6) return Constants.NAN;
            Integer next_position = ludoService.play(dice);
            if(next_position != 100) return "next_position: "+String.valueOf(next_position);
            else {
                if(ludoService.getCount()%2 == 0) return Constants.PLAYER_1_WIN;
                else return Constants.PLAYER_2_WIN;
            }
        }else return Constants.NOT_READY;
    }

    @GetMapping("/stop")
    public String stop(){
        return ludoService.stop();
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
