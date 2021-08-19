package org.apache.hive.beeline;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class KerberosBeeLine {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: " + KerberosBeeLine.class.getName() + " <config file>");
            System.exit(1);
        }

        Properties props = loadFile(args[0]);
        String krb5Conf = props.getProperty("krb5Conf");
        Objects.requireNonNull(krb5Conf, "krb5Conf not set");
        krb5Conf = requireFileExists(krb5Conf).getAbsolutePath();
        System.err.println("Krb5Conf: " + krb5Conf);
        System.setProperty("java.security.krb5.conf", krb5Conf);

        String jdbcUrl = props.getProperty("jdbcUrl");
        Objects.requireNonNull(krb5Conf, "jdbcUrl not set");
        System.err.println("JdbcUrl: " + krb5Conf);

        String principal = props.getProperty("principal");
        Objects.requireNonNull(principal, "principal not set");
        System.err.println("Principal: " + principal);

        String keytab = props.getProperty("keytab");
        Objects.requireNonNull(principal, "keytab not set");
        keytab = requireFileExists(keytab).getAbsolutePath();
        System.err.println("Keytab: " + krb5Conf);

        Configuration conf = new Configuration();
        conf.set("hadoop.security.authentication", "kerberos");
        UserGroupInformation.setConfiguration(conf);
        UserGroupInformation.loginUserFromKeytab(principal, keytab);
        BeeLine.main(new String[]{"-u", jdbcUrl});
    }

    static Properties loadFile(String file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            Properties props = new Properties();
            props.load(is);
            return props;
        }
    }

    static File requireFileExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not exists: " + file.getAbsolutePath());
        }
        return file;
    }
}
