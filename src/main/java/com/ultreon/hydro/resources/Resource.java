package com.ultreon.bubbles.resources;

import com.ultreon.commons.function.ThrowingSupplier;
import com.ultreon.hydro.Hydro;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;

public class Resource {
    protected ThrowingSupplier<InputStream, IOException> opener;
    private byte[] data;

    public Resource(ThrowingSupplier<InputStream, IOException> opener) {
        this.opener = opener;
    }

    public void load() {
        try (InputStream inputStream = opener.get()) {
            this.data = inputStream.readAllBytes();
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    public byte[] loadOrGet() {
        if (data == null) {
            load();
        }

        return getData();
    }

    public InputStream loadOrOpenStream() {
        return new ByteArrayInputStream(loadOrGet());
    }

    protected Image loadImage() {
        try (InputStream inputStream = opener.get()) {
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    public byte[] getData() {
        return data;
    }

    public ByteArrayInputStream openStream() {
        return new ByteArrayInputStream(data);
    }

    public Font loadFont() throws FontFormatException {
        try (InputStream inputStream = opener.get()) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            Hydro.get().registerFont(font);
            return font;
        } catch (IOException e) {
            throw new IOError(e);
        }
    }
}
