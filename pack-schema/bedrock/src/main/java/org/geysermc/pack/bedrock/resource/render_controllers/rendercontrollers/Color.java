package org.geysermc.pack.bedrock.resource.render_controllers.rendercontrollers;

import java.lang.String;

/**
 * Color
 * <p>
 * The color to apply.
 */
public class Color {
  public String r;

  public String g;

  public String b;

  public String a;

  /**
   * A color definition in molang, between 0 and 1.
   *
   * @return Molang Color
   */
  public String r() {
    return this.r;
  }

  /**
   * A color definition in molang, between 0 and 1.
   *
   * @param r Molang Color
   */
  public void r(String r) {
    this.r = r;
  }

  /**
   * A color definition in molang, between 0 and 1.
   *
   * @return Molang Color
   */
  public String g() {
    return this.g;
  }

  /**
   * A color definition in molang, between 0 and 1.
   *
   * @param g Molang Color
   */
  public void g(String g) {
    this.g = g;
  }

  /**
   * A color definition in molang, between 0 and 1.
   *
   * @return Molang Color
   */
  public String b() {
    return this.b;
  }

  /**
   * A color definition in molang, between 0 and 1.
   *
   * @param b Molang Color
   */
  public void b(String b) {
    this.b = b;
  }

  /**
   * A color definition in molang, between 0 and 1.
   *
   * @return Molang Color
   */
  public String a() {
    return this.a;
  }

  /**
   * A color definition in molang, between 0 and 1.
   *
   * @param a Molang Color
   */
  public void a(String a) {
    this.a = a;
  }
}
