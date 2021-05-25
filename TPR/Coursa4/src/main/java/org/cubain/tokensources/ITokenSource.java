package org.cubain.tokensources;

import org.cubain.objects.TextToken;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;

public interface ITokenSource {
    Map<TextToken, List<String>> getTokens();
}
