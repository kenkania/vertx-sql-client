/*
 * Copyright (C) 2017 Julien Viet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.julienviet.pgclient.impl.provider;

import com.julienviet.pgclient.impl.CommandBase;
import com.julienviet.pgclient.impl.Connection;
import com.julienviet.pgclient.impl.PreparedStatement;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.function.Function;

abstract class ConnectionProxy implements Connection, Connection.Holder {

  private final Connection conn;

  ConnectionProxy(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Connection connection() {
    return this;
  }

  @Override
  public boolean isSsl() {
    return conn.isSsl();
  }

  @Override
  public void schedule(CommandBase cmd, Handler<Void> completionHandler) {
    conn.schedule(cmd, completionHandler);
  }

  @Override
  public void schedulePrepared(String sql, Function<AsyncResult<PreparedStatement>, CommandBase> supplier, Handler<Void> completionHandler) {
    conn.schedulePrepared(sql, supplier, completionHandler);
  }

  /**
   * Close the underlying connection
   */
  void close() {
    conn.close(this);
  }
}