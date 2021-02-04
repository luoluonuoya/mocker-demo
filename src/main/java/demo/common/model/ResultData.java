package demo.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回结果
 */
@ApiModel(value = "返回结果")
@Data
public class ResultData<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  // 处理成功
  public static final int OK = 0;
  public static final String OK_MSG = "操作成功";
  // 参数校验有误
  public static final int PARAM_VALID= 6;
  public static final String PARAM_VALID_MSG = "参数校验失败";
  // 查询结果为空
  public static final int NOTFOUND= 2;
  public static final String NOTFOUND_MSG = "查询结果为空";
  // 操作失败
  public static final int FAIL= 3;
  public static final String FAIL_MSG = "操作失败";
  // 无效token
  public static final int TOKEN= 4;
  public static final String TOKEN_MSG = "无效token";
  //超时
  public static final int TIME_OUT = 9;
  public static final String TIME_OUT_MSG = "超时";
  // 系统异常
  public static final int ERR = 1;
  public static final String ERR_MSG = "系统异常";

  public static final ResultData SUCCESS = new ResultData(OK, OK_MSG);


  @ApiModelProperty(value = "状态码")
  private int err = ERR;
  @ApiModelProperty(value = "状态描述")
  private String errmsg = "";
  @ApiModelProperty(value = "数据")
  private T data;

  /**
   * 查询结果为空
   *
   * @return
   */
  public static ResultData getNotFoundResult() {
    return new ResultData(NOTFOUND,NOTFOUND_MSG);
  }

  /**
   * 参数校验失败
   *
   * @return
   */
  public static ResultData getParamValidResult(String message) {
    return new ResultData(PARAM_VALID,message);
  }

  /**
   * 超时
   *
   * @return
   */
  public static ResultData getTimeOutResult() {
    return new ResultData(TIME_OUT,TIME_OUT_MSG);
  }

  /**
   * 失败
   *
   * @return
   */
  public static ResultData getFailResult() {
    return new ResultData(FAIL,FAIL_MSG);
  }

  /**
   * 失败
   *
   * @param message
   * @return
   */
  public static ResultData getFailResult(String message) {
    return new ResultData(FAIL,message);
  }

  /**
   * 成功
   *
   * @param message
   * @return
   */
  public static ResultData getSuccessResult(String message) {
    return new ResultData(OK,message);
  }

  /**
   * 成功
   *
   * @param data
   * @param <T>
   * @return
   */
  public static <T> ResultData getSuccessData(T data) {
    return new ResultData(OK,OK_MSG,data);
  }

  /**
   * 成功
   *
   * @param data
   * @param message
   * @return
   */
  public static <T> ResultData getSuccessResult(T data, String message) {
    return new ResultData(OK,message,data);
  }

  public ResultData() {}

  public ResultData(int err, String errmsg) {
    this.err = err;
    this.errmsg = errmsg;
  }

  public ResultData(int err, String errmsg, T result) {
    this.err = err;
    this.errmsg = errmsg;
    this.data = result;
  }

  public ResultData(T result) {
    this(OK,OK_MSG,result);
  }

}
