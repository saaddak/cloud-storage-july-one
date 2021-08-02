package netty;

import java.nio.file.Path;
import java.nio.file.Paths;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import model.AbstractCommand;
import model.CopyRequest;
import model.ListResponse;
import model.CopyResponse;

@Slf4j
public class CommandHandler extends SimpleChannelInboundHandler<AbstractCommand> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractCommand command) throws Exception {
        log.debug("Поступила команда: {}", command.getType());
        switch (command.getType()) {
            case LIST_REQUEST: // предусмотреть навигацию
                ctx.writeAndFlush(new ListResponse(Paths.get("server", "serverFiles")));
                break;
            case COPY_REQUEST:
                // логика обработки файла
                ctx.writeAndFlush(new CopyResponse();
                ctx.writeAndFlush(new ListResponse(Paths.get("server", "serverFiles"))); // обновление списка
                break;
        }
    }
}
