package com.almondtools.antlr4multichannel;

import static java.util.Arrays.copyOf;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;

public class MultiChannelTokenStream extends BufferedTokenStream {

	private int[] channels = new int[] { Token.DEFAULT_CHANNEL };

	public MultiChannelTokenStream(TokenSource tokenSource) {
		super(tokenSource);
	}

	public void enable(int channel) {
		for (int existingChannel : channels) {
			if (channel == existingChannel) {
				return;
			}
		}
		int len = channels.length;
		channels = copyOf(channels, len + 1);
		channels[len] = channel;
		int i = p - 1;
		while (i >= 0) {
			Token token = tokens.get(i);
			if (token.getChannel() == channel || !matches(token.getChannel(), channels)) {
				i--;
			} else {
				break;
			}
		}
		p = i + 1;
	}

	public void disable(int channel) {
		int remainder = 0;
		for (int i = 0; i < channels.length; i++) {
			if (channels[i] == channel) {
				continue;
			} else {
				channels[remainder] = channels[i];
				remainder++;
			}
		}
		channels = copyOf(channels, remainder);
	}

	@Override
	protected int adjustSeekIndex(int i) {
		return nextTokenOnChannel(i, channels);
	}

	@Override
	protected Token LB(int k) {
		if (k == 0 || (p - k) < 0) {
			return null;
		}
		int i = p;
		for (int  n = 1; n <= k; n++) {
			i = previousTokenOnChannel(i - 1, channels);
		}
		if (i < 0) {
			return null;
		} else {
			return tokens.get(i);
		}
	}

	@Override
	public Token LT(int k) {
		lazyInit();
		if (k == 0) {
			return null;
		}
		if (k < 0) {
			return LB(-k);
		}

		int i = p;
		for (int n = 1;n < k; n++) {
			if (sync(i + 1)) {
				i = nextTokenOnChannel(i + 1, channels);
			}
		}
		return tokens.get(i);
	}

	public int getNumberOfOnChannelTokens() {
		int n = 0;
		fill();
		for (int i = 0; i < tokens.size(); i++) {
			Token t = tokens.get(i);
			for (int channel : channels) {
				if (t.getChannel() == channel) {
					n++;
				}
			}
			if (t.getType() == Token.EOF) {
				break;
			}
		}
		return n;
	}

	protected int nextTokenOnChannel(int i, int[] channels) {
		sync(i);
		if (i >= size()) {
			return size() - 1;
		}

		Token token = tokens.get(i);
		while (!matches(token.getChannel(), channels)) {
			if (token.getType() == Token.EOF) {
				return i;
			}
			i++;
			sync(i);
			token = tokens.get(i);
		}
		return i;
	}

	protected int previousTokenOnChannel(int i, int[] channels) {
		sync(i);
		if (i >= size()) {
			return size() - 1;
		}

		while (i >= 0) {
			Token token = tokens.get(i);
			if (token.getType() == Token.EOF || matches(token.getChannel(), channels)) {
				return i;
			}
			i--;
		}
		return i;
	}

	private boolean matches(int channel, int[] channels) {
		for (int matchChannel : channels) {
			if (matchChannel == channel) {
				return true;
			}
		}
		return false;
	}
}
